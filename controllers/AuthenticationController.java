package za.co.wirecard.channel.backoffice.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientResponseException;
import za.co.wirecard.channel.backoffice.config.JwtSession;
import za.co.wirecard.channel.backoffice.dto.models.requests.ForgotPassword;
import za.co.wirecard.channel.backoffice.dto.models.requests.Login;
import za.co.wirecard.channel.backoffice.dto.models.requests.TwoFactorAuth;
import za.co.wirecard.channel.backoffice.entities.BackOfficeRouteEntity;
import za.co.wirecard.channel.backoffice.entities.BackOfficeUserEntity;
import za.co.wirecard.channel.backoffice.entities.BackOfficeUserLoginEntity;
import za.co.wirecard.channel.backoffice.entities.BackOfficeUserPasswordHistoryEntity;
import za.co.wirecard.channel.backoffice.exceptions.GenericException;
import za.co.wirecard.channel.backoffice.models.*;
import za.co.wirecard.channel.backoffice.repositories.BackOfficeRouteRepository;
import za.co.wirecard.channel.backoffice.repositories.BackOfficeUserPasswordHistoryRepository;
import za.co.wirecard.channel.backoffice.repositories.BackOfficeUserRepository;
import za.co.wirecard.channel.backoffice.services.*;
import za.co.wirecard.common.exceptions.UserNotFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

//Delete line 13 in production
@CrossOrigin
@RestController
@RequestMapping(value = "/channel-back-office/api/v1/authentication")
public class AuthenticationController {

    private static final Logger logger = LogManager.getLogger(AuthenticationController.class);
    private final AuthenticationService authService;
    private final GoogleAuthenticatorService googleAuthenticatorService;
    private final TokenService tokenService;
    private final BackOfficeUserRepository backOfficeUserRepository;
    private final TwoFAService twoFAService;
    private final UserService userService;
    private final BackOfficeRouteRepository backOfficeRouteRepository;
    private final BackOfficeUserPasswordHistoryRepository backOfficeUserPasswordHistoryRepository;

    public AuthenticationController(AuthenticationService authService,
                                    GoogleAuthenticatorService googleAuthenticatorService, TokenService tokenService, BackOfficeUserRepository backOfficeUserRepository, TwoFAService twoFAService, UserService userService, BackOfficeRouteRepository backOfficeRouteRepository, BackOfficeUserPasswordHistoryRepository backOfficeUserPasswordHistoryRepository) {
        this.authService = authService;
        this.googleAuthenticatorService = googleAuthenticatorService;
        this.tokenService = tokenService;
        this.backOfficeUserRepository = backOfficeUserRepository;
        this.twoFAService = twoFAService;
        this.userService = userService;
        this.backOfficeRouteRepository = backOfficeRouteRepository;
        this.backOfficeUserPasswordHistoryRepository = backOfficeUserPasswordHistoryRepository;
    }

    // @CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
    @GetMapping("")
    public ResponseEntity<Login> getToken(@RequestParam String username, @RequestParam String password, HttpSession session, HttpServletRequest servletRequest) {
        String ipAddress = getIpAddressFromServletRequest(servletRequest);
        Token token;
        try {
            token = authService.getToken(username, password);
            BackOfficeUserEntity backOfficeUserEntity = backOfficeUserRepository.getOne((long) token.getUserId());
            authService.deleteLoginAttempts(ipAddress, backOfficeUserEntity, token);
            authService.auditLogin(backOfficeUserEntity);
            // Check password history
            if (!backOfficeUserPasswordValid(backOfficeUserEntity)) {
                // this.authService.forgotPassword(new ForgotPassword(backOfficeUserEntity.getEmail()));
                throw new GenericException("User's password is older than 60 days", HttpStatus.PRECONDITION_FAILED, "Back office user's password is older than 60 days");
            }
            JwtSession jwtSession = new JwtSession();
            jwtSession.setToken(token);
            session.setAttribute("token", jwtSession);
            // TokenCache tokenCache = new TokenCache(token, token.getUserId());
            // tokenService.cacheToken(tokenCache);
//            if (backOfficeUserEntity == null) {
//                throw new GenericException("User not found", HttpStatus.NOT_FOUND, "Could not find user with id | " + token.getUserId());
//            }
            Login login = new Login(token.getUserId(), backOfficeUserEntity, twoFAService, false);
            if (!login.isTwoFactorAuthEnabled()) {
                twoFAService.sendOtp(backOfficeUserEntity);
            }
            return ResponseEntity.ok(login);
        } catch (RestClientResponseException e) {
            BackOfficeUserLoginEntity backOfficeUserLoginEntity = authService.auditLoginAttempt(ipAddress, username);
            if (backOfficeUserLoginEntity != null) {
                throw new GenericException("Invalid username/password", HttpStatus.UNAUTHORIZED, "You have " + (6 - backOfficeUserLoginEntity.getLoginAttemptCount()) + " attempt(s) left");
            }
            throw new GenericException("Invalid username/password", HttpStatus.UNAUTHORIZED, "Invalid username/password");
        }
    }

    @GetMapping("/resetTwoFa/{userId}")
    public ResponseEntity<Login> getToken(@PathVariable long userId, HttpSession session) {
        try {
            BackOfficeUserEntity backOfficeUserEntity = backOfficeUserRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
            twoFAService.sendOtp(backOfficeUserEntity);
            Login login = new Login((int) userId, backOfficeUserEntity, twoFAService, true);
            return ResponseEntity.ok(login);
        } catch (RestClientResponseException e) {
            throw new GenericException(e.getMessage(), HttpStatus.valueOf(e.getRawStatusCode()) == HttpStatus.FORBIDDEN ? HttpStatus.UNAUTHORIZED : HttpStatus.valueOf(e.getRawStatusCode()), e.getResponseBodyAsString());
        }
    }

    @PostMapping("/2fa/enable/{userId}")
    public void enableTwoFA(@PathVariable long userId) {
        try {
            twoFAService.enableTwoFA(userId);
        } catch (RestClientResponseException e) {
            throw new GenericException("Invalid Credentials", HttpStatus.UNAUTHORIZED, "Invalid Username/Password combination");
        }
    }

    @PostMapping("/2fa/otp/{userId}/{otp}")
    public ResponseEntity<?> confirmOtp(@PathVariable long userId, @PathVariable String otp) {
        ResponseEntity<?> responseEntity;
        try {
            responseEntity = twoFAService.confirmOtp(userId, otp);
        } catch (RestClientResponseException e) {
            throw new GenericException("Invalid Otp", HttpStatus.UNAUTHORIZED, "Invalid Otp");
        }
        return responseEntity;
    }

    @PostMapping("/2fa/resend-otp/{userId}")
    public ResponseEntity<?> resendOtp(@PathVariable long userId) {
        ResponseEntity<?> responseEntity;
        try {
            responseEntity = twoFAService.resendOtp(userId);
        } catch (RestClientResponseException e) {
            throw new GenericException("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR, e.getResponseBodyAsString());
        }
        return responseEntity;
    }


    @GetMapping("/getVerifyToken")
    public TokenVerify getVerifyToken() {
        logger.info("Auth: Verify User");
        return authService.getTokenVerify();
    }

    @PostMapping("/forgot-password")
    public void forgotPassword(@RequestBody @Valid ForgotPassword forgotPassword, HttpServletRequest servletRequest) {
        logger.info("Processing forgot password call for email | " + forgotPassword.getUsername());
        this.authService.forgotPassword(forgotPassword, servletRequest);
    }

    @PostMapping("/2fa/{userId}")
    public Token returnAccessToken(@RequestBody @Valid TwoFactorAuth twoFactorAuth, @PathVariable long userId, HttpSession session) {
        try {
            googleAuthenticatorService.twoFactorAuth(twoFactorAuth.getCode(), userId);
            JwtSession jwtSession = (JwtSession) session.getAttribute("token");
            if (jwtSession == null) {
                throw new GenericException("Could not identify session", HttpStatus.NOT_FOUND, "Could not find any session for this user | " + userId);
            }
            Token token = jwtSession.getToken();
            List<RouteConfig> routeConfigs = userService.constructPermissions(token);
            logger.info("ROUTE CONFIG | " + routeConfigs.toString());
            List<BackOfficeRouteEntity> backOfficeRouteEntities = backOfficeRouteRepository.findAll();
            List<RouteConfig> routeConfigz = new ArrayList<>();
            for (BackOfficeRouteEntity backOfficeRouteEntity1 : backOfficeRouteEntities) {
                logger.info("Back office route | " + backOfficeRouteEntity1.getCode());
                List<RouteConfig> routeConfigEachLoop = new ArrayList<>();
                List<String> routeConfigs1 = routeConfigs
                        .stream()
                        .map(routeConfig -> {
                            // If route matches the back office route in for loop, and routeConfigz doesn't already contain it, collect the crud operations of it.
                            // && routeConfigz.stream().noneMatch(routeConfig1 -> routeConfig1.getAllowedRoute().equalsIgnoreCase(backOfficeRouteEntity1.getCode()))
                            if (routeConfig.getAllowedRoute().equalsIgnoreCase(backOfficeRouteEntity1.getCode()) && !routeConfigEachLoop.contains(routeConfig)) {
                                logger.info("Yes, push " + routeConfig.getAllowedCrud().get(0) + " into | " + routeConfig.getAllowedRoute());
                                // Add to arr
                                routeConfigEachLoop.add(new RouteConfig(routeConfig.getAllowedRoute(), routeConfig.getAllowedCrud()));
                                // Return to new
                                return routeConfig.getAllowedCrud().get(0);
                            }
                            return null;
                        })
                        .collect(Collectors.toList());
                List<String> routeConfigzNotNull = routeConfigs1
                        .stream()
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());
                if (routeConfigzNotNull != null && routeConfigzNotNull.size() > 0) {
                    routeConfigz.add(new RouteConfig(backOfficeRouteEntity1.getCode(), routeConfigzNotNull));
                }
                routeConfigEachLoop.clear();
            }
            logger.info("ROUTE CONFIGZ | " + routeConfigz.toString());
            token.setRouteConfig(routeConfigz);
            return token;
//            List<RouteConfig> routeConfigz = new ArrayList<>();
//            List<RouteConfig> routeConfigsz = new ArrayList<>();
//            List<String> allowedCrud1 = new ArrayList<>();
//            for (RouteConfig routeConfig: routeConfigs) {
//                logger.info("Route obj | " + routeConfig.toString());
//                if (routeConfigz.size() > 0) {
//                    for (RouteConfig routeConfig1: routeConfigz) {
//                        if (routeConfig1.getAllowedRoute().equalsIgnoreCase(routeConfig.getAllowedRoute())) {
//                            logger.info("What does allowedCrud contain | " + allowedCrud1.toString());
//                            if (!allowedCrud1.contains(routeConfig.getAllowedCrud().get(0))) {
//                                logger.info("Add | " + routeConfig.getAllowedCrud().get(0) + " to it");
//                                allowedCrud1.add(routeConfig.getAllowedCrud().get(0));
//                                if (routeConfigs.indexOf(routeConfig)+1 == routeConfigs.size()) {
//                                    logger.info("Last EL | End of | " + routeConfig1.toString());
//                                    logger.info("Allowed Crud for it | " + allowedCrud1);
//                                    RouteConfig routeConfig2 = new RouteConfig();
//                                    routeConfig2.setAllowedCrud(allowedCrud1);
//                                    routeConfig2.setAllowedRoute(routeConfig1.getAllowedRoute());
//                                    routeConfig1.setAllowedCrud(allowedCrud1);
//                                    if (!routeConfigsz.contains(routeConfig2)) {
//                                        logger.info("PUT NEW | " + routeConfig2.toString());
//                                        routeConfigsz.add(routeConfig2);
//                                    }
//                                    logger.info("routeConfigsz | " + routeConfigsz.toString());
//                                    logger.info("routeConfigz | " + routeConfigz.toString());
//                                    routeConfigz = new ArrayList<>();
//                                    allowedCrud1 = new ArrayList<>();
//                                }
//                            }
//                        } else {
//                            logger.info("End of | " + routeConfig1.toString());
//                            logger.info("Allowed Crud for it | " + allowedCrud1);
//                            RouteConfig routeConfig2 = new RouteConfig();
//                            routeConfig2.setAllowedCrud(allowedCrud1);
//                            routeConfig2.setAllowedRoute(routeConfig1.getAllowedRoute());
//                            routeConfig1.setAllowedCrud(allowedCrud1);
//                            if (!routeConfigsz.contains(routeConfig2)) {
//                                logger.info("PUT NEW | " + routeConfig2.toString());
//                                routeConfigsz.add(routeConfig2);
//                            }
//                            logger.info("routeConfigsz | " + routeConfigsz.toString());
//                            logger.info("routeConfigz | " + routeConfigz.toString());
//                            logger.info("INDEX!!!! " + (routeConfigs.indexOf(routeConfig)+1) + " " + routeConfigs.size());
//                            if ((routeConfigs.indexOf(routeConfig)+1) == routeConfigs.size()) {
//                                logger.info("Add to route configs");
//                                logger.info("Add for real...");
//                                routeConfigsz.add(routeConfig);
//                            } else {
//                                routeConfigz = new ArrayList<>();
//                                routeConfigz.add(routeConfig);
//                                allowedCrud1 = new ArrayList<>();
//                                allowedCrud1.add(routeConfig.getAllowedCrud().get(0));
//                            }
//
//                            break;
//                        }
//                    }
//                } else {
//                    logger.info("Add default... " + routeConfig.toString());
//                    routeConfigz.add(routeConfig);
//                    allowedCrud1.add(routeConfig.getAllowedCrud().get(0));
//                }
//            }
//            logger.info("After routeConfig for loop | " + routeConfigsz);
//            token.setRouteConfig(routeConfigsz);
//            return token;
        } catch (GenericException e) {
            throw new GenericException(e.getReason(), e.getHttpStatus(), e.getResponse());
        }

//        Optional<TokenCache> tokenCache = tokenService.getTokenById((int) userId);
//        if (!tokenCache.isPresent()) {
//            throw new GenericException("No token found for user", HttpStatus.NOT_FOUND, "No token for user");
//        }
        // Get token;
        // return token;
    }

    private boolean backOfficeUserPasswordValid(BackOfficeUserEntity backOfficeUserEntity) {
        BackOfficeUserPasswordHistoryEntity backOfficeUserPasswordHistoryEntity =
                backOfficeUserPasswordHistoryRepository.findDistinctFirstByBackOfficeUserByBackOfficeUserIdOrderByIdDesc(backOfficeUserEntity);
        if (backOfficeUserPasswordHistoryEntity == null) {
            // Create entry
            BackOfficeUserPasswordHistoryEntity backOfficeUserPasswordHistoryEntity1 = new BackOfficeUserPasswordHistoryEntity();
            backOfficeUserPasswordHistoryEntity1.setBackOfficeUserByBackOfficeUserId(backOfficeUserEntity);
            backOfficeUserPasswordHistoryEntity1.setPassword(backOfficeUserEntity.getPassword());
            backOfficeUserPasswordHistoryEntity1.setCreatedDate(new Timestamp(System.currentTimeMillis()));
            backOfficeUserPasswordHistoryRepository.save(backOfficeUserPasswordHistoryEntity1);
            return true;
        }

        // Check if password is 90 days old or older.

        Calendar cPasswordValidityCutoffTime = Calendar.getInstance();
        cPasswordValidityCutoffTime.set(Calendar.HOUR_OF_DAY, -1440);
        cPasswordValidityCutoffTime.set(Calendar.MINUTE, 0);
        cPasswordValidityCutoffTime.set(Calendar.SECOND, 0);
        cPasswordValidityCutoffTime.set(Calendar.MILLISECOND, 0);

        logger.info("Password created date nanos | " + backOfficeUserPasswordHistoryEntity.getCreatedDate().getTime());
        logger.info("Password 90 day cutoff millis | " + cPasswordValidityCutoffTime.getTimeInMillis());

        if (backOfficeUserPasswordHistoryEntity.getCreatedDate().getTime() <= cPasswordValidityCutoffTime.getTimeInMillis()) {
         return false;
        }

        return true;
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
