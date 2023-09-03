package za.co.wirecard.channel.backoffice.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import za.co.wirecard.channel.backoffice.config.VerifyUserSession;
import za.co.wirecard.channel.backoffice.dto.models.requests.PlatformVerifyUser;
import za.co.wirecard.channel.backoffice.dto.models.responses.PlatformGetUnverifiedUserByUid;
import za.co.wirecard.channel.backoffice.entities.BackOfficeUserEntity;
import za.co.wirecard.channel.backoffice.entities.BackOfficeUserPasswordHistoryEntity;
import za.co.wirecard.channel.backoffice.entities.UnverifiedBackOfficeUserEntity;
import za.co.wirecard.channel.backoffice.exceptions.GenericException;
import za.co.wirecard.channel.backoffice.models.TokenVerify;
import za.co.wirecard.channel.backoffice.repositories.BackOfficeUserPasswordHistoryRepository;
import za.co.wirecard.channel.backoffice.repositories.BackOfficeUserRepository;
import za.co.wirecard.channel.backoffice.repositories.UnverifiedBackOfficeUserRepository;
import za.co.wirecard.common.exceptions.UserNotFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.sql.Timestamp;

@Service
public class UserVerifyService {

    private static final Logger logger = LogManager.getLogger(UserVerifyService.class);
    private final UnverifiedBackOfficeUserRepository unverifiedBackOfficeUserRepository;
    private final BackOfficeUserRepository backOfficeUserRepository;
    private final AuthenticationService authenticationService;
    private final BackOfficeUserPasswordHistoryRepository backOfficeUserPasswordHistoryRepository;

    public UserVerifyService(UnverifiedBackOfficeUserRepository unverifiedBackOfficeUserRepository, BackOfficeUserRepository backOfficeUserRepository, AuthenticationService authenticationService, BackOfficeUserPasswordHistoryRepository backOfficeUserPasswordHistoryRepository) {
        this.unverifiedBackOfficeUserRepository = unverifiedBackOfficeUserRepository;
        this.backOfficeUserRepository = backOfficeUserRepository;
        this.authenticationService = authenticationService;
        this.backOfficeUserPasswordHistoryRepository = backOfficeUserPasswordHistoryRepository;
    }

    public PlatformGetUnverifiedUserByUid getUnverifiedUser(String uid, HttpSession session) {
        logger.info("Return unverified user with uid | " + uid);
        UnverifiedBackOfficeUserEntity unverifiedBackOfficeUserEntity = unverifiedBackOfficeUserRepository
                .findByUuid(uid)
                .orElseThrow(() -> new GenericException("No user with that uid exists", HttpStatus.NOT_FOUND, "Could not find a user with uid " + uid));

        TokenVerify tokenVerify = authenticationService.getTokenVerify();
        tokenVerify.setUserId(unverifiedBackOfficeUserEntity.getBackOfficeUserByUserId().getId());
        VerifyUserSession verifyUserSession = new VerifyUserSession(tokenVerify);
        session.setAttribute("tokenVerify", verifyUserSession);
        return new PlatformGetUnverifiedUserByUid(unverifiedBackOfficeUserEntity.getBackOfficeUserByUserId().getId());
    }

    @Transactional
    public void verifyUser(long id, PlatformVerifyUser platformVerifyUser, HttpServletRequest servletRequest) {
        logger.info("Verify unverified user with id | " + id);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(4);
        BackOfficeUserEntity backOfficeUserEntity = backOfficeUserRepository
                .findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "createdDate"));

        Page<BackOfficeUserPasswordHistoryEntity> backOfficeUserPasswordHistoryEntitiesPaged = backOfficeUserPasswordHistoryRepository.findAllByBackOfficeUserByBackOfficeUserId(backOfficeUserEntity, pageable);
        if (backOfficeUserPasswordHistoryEntitiesPaged != null && backOfficeUserPasswordHistoryEntitiesPaged.getSize() > 0) {
            logger.info("New potential, encoded password | " + passwordEncoder.encode(platformVerifyUser.getPassword()));
            backOfficeUserPasswordHistoryEntitiesPaged.getContent()
                    .forEach(backOfficeUserPasswordHistoryEntity -> {
                        if (passwordEncoder.matches(platformVerifyUser.getPassword(), backOfficeUserPasswordHistoryEntity.getPassword())) {
                            throw new GenericException("Password must differ from previous passwords set", HttpStatus.CONFLICT, "Password must differ from previous passwords set");
                        }
                    });
        }

        backOfficeUserEntity.setPassword(passwordEncoder.encode(platformVerifyUser.getPassword()));
        backOfficeUserEntity.setActive(true);
        backOfficeUserRepository.save(backOfficeUserEntity);
        unverifiedBackOfficeUserRepository.deleteAllByBackOfficeUserByUserId(backOfficeUserEntity);

        BackOfficeUserPasswordHistoryEntity backOfficeUserPasswordHistoryEntity = new BackOfficeUserPasswordHistoryEntity();
        backOfficeUserPasswordHistoryEntity.setPassword(backOfficeUserEntity.getPassword());
        backOfficeUserPasswordHistoryEntity.setBackOfficeUserByBackOfficeUserId(backOfficeUserEntity);
        backOfficeUserPasswordHistoryEntity.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        backOfficeUserPasswordHistoryRepository.save(backOfficeUserPasswordHistoryEntity);

    }

}
