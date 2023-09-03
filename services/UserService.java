package za.co.wirecard.channel.backoffice.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import za.co.wirecard.channel.backoffice.config.Utils;
import za.co.wirecard.channel.backoffice.dto.models.ResetPassword;
import za.co.wirecard.channel.backoffice.dto.models.UserRole;
import za.co.wirecard.channel.backoffice.entities.*;
import za.co.wirecard.channel.backoffice.exceptions.EmailAlreadyInUseException;
import za.co.wirecard.channel.backoffice.exceptions.GenericException;
import za.co.wirecard.channel.backoffice.models.*;
import za.co.wirecard.channel.backoffice.models.email.EmailNotification;
import za.co.wirecard.channel.backoffice.mq.SendEmail;
import za.co.wirecard.channel.backoffice.repositories.*;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Value("${api.usermanagement.url}")
    private String userManagementUrl;
    @Value("${application.external.endpoints.activate-user}")
    private String activateUserUrl;

    private final BackOfficeUserRepositoryPaging backOfficeUserRepositoryPaging;
    private final BackOfficeUserRepository backOfficeUserRepository;
    private final BackOfficeGroupRepository backOfficeGroupRepository;
    private final BackOfficeGroupPermissionRepository backOfficeGroupPermissionRepository;
    private final BackOfficeRouteRepository backOfficeRouteRepository;
    private final BackOfficePermissionRepository backOfficePermissionRepository;
    private final UnverifiedBackOfficeUserRepository unverifiedBackOfficeUserRepository;
    private final JavaMailSender javaMailSender;
    private final SendEmail sendEmail;
    private final TwoFAService twoFAService;
    private final AuthenticationService authenticationService;
    private final BackOfficeUserPasswordHistoryRepository backOfficeUserPasswordHistoryRepository;
    private final BackOfficeUserLoginRepository backOfficeUserLoginRepository;

    private static final Logger logger = LogManager.getLogger(UserService.class);

    public UserService(BackOfficeUserRepositoryPaging backOfficeUserRepositoryPaging, BackOfficeUserRepository backOfficeUserRepository, BackOfficeGroupRepository backOfficeGroupRepository, BackOfficeGroupPermissionRepository backOfficeGroupPermissionRepository, BackOfficeRouteRepository backOfficeRouteRepository, BackOfficePermissionRepository backOfficePermissionRepository, UnverifiedBackOfficeUserRepository unverifiedBackOfficeUserRepository, JavaMailSender javaMailSender, SendEmail sendEmail, TwoFAService twoFAService, AuthenticationService authenticationService, BackOfficeUserPasswordHistoryRepository backOfficeUserPasswordHistoryRepository, BackOfficeUserLoginRepository backOfficeUserLoginRepository) {
        this.backOfficeUserRepositoryPaging = backOfficeUserRepositoryPaging;
        this.backOfficeUserRepository = backOfficeUserRepository;
        this.backOfficeGroupRepository = backOfficeGroupRepository;
        this.backOfficeGroupPermissionRepository = backOfficeGroupPermissionRepository;
        this.backOfficeRouteRepository = backOfficeRouteRepository;
        this.backOfficePermissionRepository = backOfficePermissionRepository;
        this.unverifiedBackOfficeUserRepository = unverifiedBackOfficeUserRepository;
        this.javaMailSender = javaMailSender;
        this.sendEmail = sendEmail;
        this.twoFAService = twoFAService;
        this.authenticationService = authenticationService;
        this.backOfficeUserPasswordHistoryRepository = backOfficeUserPasswordHistoryRepository;
        this.backOfficeUserLoginRepository = backOfficeUserLoginRepository;
    }

    public Page<BackOfficeUserEntity> getUsers(int page, int limit, HttpServletRequest servletRequest) {
        Pageable pageable = PageRequest.of(page, limit, Sort.by("id").descending());
        Page<BackOfficeUserEntity> platformGetUsersByMerchantIdResponses = backOfficeUserRepository.findAll(pageable);
        return platformGetUsersByMerchantIdResponses;

    }

    public BackOfficeUserEntity getUser(long userId, HttpServletRequest servletRequest) {
        BackOfficeUserEntity backOfficeUserEntity = backOfficeUserRepository.getOne(userId);
        if (backOfficeUserEntity == null) {
            throw new GenericException("User not found", HttpStatus.NOT_FOUND, "Could not find user with id | " + userId);
        }
        return backOfficeUserEntity;
    }

    public void createUser(User user, HttpServletRequest servletRequest) {

        logger.info("Checking for duplicate email | " + user.getEmail());
        if (backOfficeUserRepository.findByEmailIgnoreCase(user.getEmail()).isPresent()) {
            logger.info("Duplicate email found. " + backOfficeUserRepository.findByEmailIgnoreCase(user.getEmail()).get().toString());
           throw new EmailAlreadyInUseException("A user with this email already exists", HttpStatus.CONFLICT, "User with email | " + user.getEmail() + " already exists");
        }
        String accessToken = servletRequest.getHeader(HttpHeaders.AUTHORIZATION).replace("Bearer ", "");
        if (accessToken == null) {
            throw new GenericException("Token information service was not passed proper parameters", HttpStatus.BAD_REQUEST, "Token derived object is null");
        }
        // Decode JWT Token from accessToken
        JWT jwt = new JWT();
        DecodedJWT decodedJWT = jwt.decodeJwt(accessToken);
        Long userId = decodedJWT.getClaims().get("userId") != null ? decodedJWT.getClaims().get("userId").asLong() : null;
        BackOfficeUserEntity backOfficeUserEntity = new BackOfficeUserEntity(user, userId);
        backOfficeUserRepository.save(backOfficeUserEntity);
        // Create unverified back office user entry
        UnverifiedBackOfficeUserEntity unverifiedBackOfficeUserEntity = new UnverifiedBackOfficeUserEntity(backOfficeUserEntity);
        unverifiedBackOfficeUserRepository.save(unverifiedBackOfficeUserEntity);
        // Send activation email
        String url = String.format(activateUserUrl, unverifiedBackOfficeUserEntity.getUuid());
        EmailNotification emailNotification = new EmailNotification(backOfficeUserEntity.getEmail(), "Back Office Activation", authenticationService.setEmailTemplate(backOfficeUserEntity.getFirstName(), url));
        sendEmail.send(emailNotification);
//        try {
//            twoFAService.sendEmail(emailNotification);
//        } catch (MessagingException e) {
//            throw new GenericException("Something went wrong with sending the email", HttpStatus.INTERNAL_SERVER_ERROR, "Email sending failed");
//        }
    }

    @Transactional
    public void updateUser(long id, User user, HttpServletRequest servletRequest) {
        BackOfficeUserEntity backOfficeUserEntity = backOfficeUserRepository.getOne(id);
        if (backOfficeUserEntity == null) {
            throw new GenericException("User not found", HttpStatus.NOT_FOUND, "Could not find user with id | " + id);
        }
        String accessToken = servletRequest.getHeader(HttpHeaders.AUTHORIZATION).replace("Bearer ", "");
        if (accessToken == null) {
            throw new GenericException("Token information service was not passed proper parameters", HttpStatus.BAD_REQUEST, "Token derived object is null");
        }
        // Decode JWT Token from accessToken
        JWT jwt = new JWT();
        DecodedJWT decodedJWT = jwt.decodeJwt(accessToken);
        Long userId = decodedJWT.getClaims().get("userId") != null ? decodedJWT.getClaims().get("userId").asLong() : null;
        if (backOfficeUserEntity == null) {
            throw new GenericException("User not found", HttpStatus.NOT_FOUND, "Could not find user with id | " + id);
        }
        Timestamp timestamp = new Timestamp(Instant.now().toEpochMilli());

        backOfficeUserEntity.setBackOfficeGroupId(user.getGroupId());
        backOfficeUserEntity.setBirthDate(user.getBirthDate());
        backOfficeUserEntity.setCell(user.getCell());
        backOfficeUserEntity.setModifiedBy(userId);
        backOfficeUserEntity.setFirstName(user.getFirstName());
        backOfficeUserEntity.setKnownAs(user.getKnownAs());
        backOfficeUserEntity.setLandline(user.getLandline());
        backOfficeUserEntity.setLastName(user.getLastName());
        backOfficeUserEntity.setLastModified(timestamp);
        backOfficeUserEntity.setIsAccountManager(false);
        backOfficeUserEntity.setIsCreditController(false);
        backOfficeUserEntity.setIsSalesPerson(false);
        for (UserRole userRole: user.getUserRoles().getUserRoles()) {
            if (userRole.getCode().equalsIgnoreCase(Utils.BACK_OFFICE_ROLE_ACCOUNT_MANAGER)) {
                backOfficeUserEntity.setIsAccountManager(true);
            } else if (userRole.getCode().equalsIgnoreCase(Utils.BACK_OFFICE_ROLE_CREDIT_CONTROLLER)) {
                backOfficeUserEntity.setIsCreditController(true);
            } else if (userRole.getCode().equalsIgnoreCase(Utils.BACK_OFFICE_ROLE_SALES_PERSON)) {
                backOfficeUserEntity.setIsSalesPerson(true);
            }
        }
        backOfficeUserEntity.setPosition(user.getPosition());
        backOfficeUserRepository.save(backOfficeUserEntity);
    }

    @Transactional
    public void deleteUser(long id, HttpServletRequest servletRequest) {
        BackOfficeUserEntity backOfficeUserEntity = backOfficeUserRepository.getOne(id);
        if (backOfficeUserEntity == null) {
            throw new GenericException("User not found", HttpStatus.NOT_FOUND, "Could not find user with id | " + id);
        }
        unverifiedBackOfficeUserRepository.deleteAllByBackOfficeUserByUserId(backOfficeUserEntity);
        backOfficeUserRepository.delete(backOfficeUserEntity);
    }

    public List<RouteConfig> constructPermissions(Token token) {
        logger.info("Constructing token for user with id | " + token.getUserId());
        BackOfficeUserEntity backOfficeUserEntity = backOfficeUserRepository.getOne((long) token.getUserId());
        if (backOfficeUserEntity == null) {
            throw new GenericException("User not found", HttpStatus.NOT_FOUND, "Could not find user with id | " + token.getUserId());
        }
        BackOfficeGroupEntity backOfficeGroupEntity = backOfficeGroupRepository.getOne(backOfficeUserEntity.getBackOfficeGroupId());
        List<BackOfficePermissionEntity> backOfficePermissionEntityList = backOfficeGroupPermissionRepository.findAllByBackOfficeGroupByBackOfficeGroupId(backOfficeGroupEntity)
                .stream()
                .map(BackOfficeGroupPermissionEntity::getBackOfficePermissionByBackOfficePermissionId)
                .collect(Collectors.toList());
        List<BackOfficePermissionEntity> filteredBackOfficePermissionEntityList = backOfficePermissionEntityList.stream().filter(backOfficePermissionEntity -> backOfficePermissionEntity.getBackOfficeRouteEntity() != null).collect(Collectors.toList());
        List<RouteCrud> routeCruds = filteredBackOfficePermissionEntityList
                .stream()
                .map(backOfficePermissionEntity -> backOfficePermissionEntity.getBackOfficeRouteEntity() != null ? new RouteCrud(backOfficePermissionEntity) : null)
                .collect(Collectors.toList());
        return routeCruds
                .stream()
                .map(RouteConfig::new)
                .collect(Collectors.toList());
    }

    public void resendActivation(long id, HttpServletRequest httpServletRequest) {
        BackOfficeUserEntity backOfficeUserEntity = backOfficeUserRepository.getOne(id);
        if (backOfficeUserEntity == null) {
            throw new GenericException("User not found", HttpStatus.NOT_FOUND, "Could not find user with id | " + id);
        }
        checkLockout(getIpAddressFromServletRequest(httpServletRequest), backOfficeUserEntity.getEmail());
        UnverifiedBackOfficeUserEntity unverifiedBackOfficeUserEntity = unverifiedBackOfficeUserRepository.findByBackOfficeUserByUserId(backOfficeUserEntity);
        if (unverifiedBackOfficeUserEntity == null) {
            throw new GenericException("Unverified user not found", HttpStatus.NOT_FOUND, "Could not find unverified user | " + backOfficeUserEntity.getEmail());
        }
        // Send activation email
        String url = String.format(activateUserUrl, unverifiedBackOfficeUserEntity.getUuid());
        EmailNotification emailNotification = new EmailNotification(backOfficeUserEntity.getEmail(), "Back Office Activation", authenticationService.setEmailTemplate(backOfficeUserEntity.getFirstName(), url));
        sendEmail.send(emailNotification);
    }

    public void resetPassword(ResetPassword resetPassword, HttpServletRequest httpServletRequest) {
        try {
            BackOfficeUserEntity backOfficeUserEntity = backOfficeUserRepository.findByEmailIgnoreCase(resetPassword.getUsername())
                    .orElseThrow(() -> new GenericException("Could not find user with username | " + resetPassword.getUsername(), HttpStatus.NOT_FOUND, "Could not find user with username | " + resetPassword.getUsername()));
            checkLockout(getIpAddressFromServletRequest(httpServletRequest), resetPassword.getUsername());
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(4);

            Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "createdDate"));

            Page<BackOfficeUserPasswordHistoryEntity> backOfficeUserPasswordHistoryEntitiesPaged = backOfficeUserPasswordHistoryRepository.findAllByBackOfficeUserByBackOfficeUserId(backOfficeUserEntity, pageable);
            if (backOfficeUserPasswordHistoryEntitiesPaged != null && backOfficeUserPasswordHistoryEntitiesPaged.getSize() > 0) {
                logger.info("New potential, encoded password | " + passwordEncoder.encode(resetPassword.getUserVerify().getPassword()));
                backOfficeUserPasswordHistoryEntitiesPaged.getContent()
                        .forEach(backOfficeUserPasswordHistoryEntity -> {
                            if (passwordEncoder.matches(resetPassword.getUserVerify().getPassword(), backOfficeUserPasswordHistoryEntity.getPassword())) {
                                throw new GenericException("Password must differ from previous passwords set", HttpStatus.CONFLICT, "Password must differ from previous passwords set");
                            }
                        });
            }

            backOfficeUserEntity.setPassword(passwordEncoder.encode(resetPassword.getUserVerify().getPassword()));
            backOfficeUserRepository.save(backOfficeUserEntity);

            BackOfficeUserPasswordHistoryEntity backOfficeUserPasswordHistoryEntity = new BackOfficeUserPasswordHistoryEntity();
            backOfficeUserPasswordHistoryEntity.setPassword(backOfficeUserEntity.getPassword());
            backOfficeUserPasswordHistoryEntity.setBackOfficeUserByBackOfficeUserId(backOfficeUserEntity);
            backOfficeUserPasswordHistoryEntity.setCreatedDate(new Timestamp(System.currentTimeMillis()));
            backOfficeUserPasswordHistoryRepository.save(backOfficeUserPasswordHistoryEntity);

        } catch (GenericException e) {
            logger.error(e);
            e.printStackTrace();
            throw new GenericException(e.getMessage(), e.getHttpStatus(), e.getMessage());
        }
    }

    public void checkLockout(String ipAddress, String username) {
        Optional<BackOfficeUserEntity> backOfficeUserEntity = backOfficeUserRepository.findByEmailIgnoreCase(username);
        if (backOfficeUserEntity.isPresent()) {
            BackOfficeUserLoginEntity backOfficeUserLoginEntity = backOfficeUserLoginRepository.findByIpAddressAndBackOfficeUserByBackOfficeUserId(ipAddress, backOfficeUserEntity.get());
            if (backOfficeUserLoginEntity == null) {
                backOfficeUserLoginEntity = new BackOfficeUserLoginEntity();
            } else {
                if (backOfficeUserLoginEntity.getLoginAttemptCount() == 5 && backOfficeUserEntity.get().getBlocked() != null && backOfficeUserEntity.get().getBlocked()) {
                    throw new GenericException("Your account is locked, please wait a while before trying again", HttpStatus.CONFLICT, "Your account is locked, please wait a while before trying again");
                }
            }
        }
    }

    private String getIpAddressFromServletRequest(HttpServletRequest request) {
        String remoteAddr = "";
        if (request != null) {
            remoteAddr = request.getHeader("X-FORWARDED-FOR");
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = request.getRemoteAddr();
            }
            if (remoteAddr.contains(",")) {
                remoteAddr = remoteAddr.replace(",", "|");
            }
        }
        if (remoteAddr.contains(",")) {
            remoteAddr.replace(",", "|");
        }
        return remoteAddr;
    }


}
