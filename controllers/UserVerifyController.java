package za.co.wirecard.channel.backoffice.controllers;

import com.google.zxing.WriterException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientResponseException;
import za.co.wirecard.channel.backoffice.dto.models.requests.PlatformVerifyUser;
import za.co.wirecard.channel.backoffice.dto.models.responses.Create2FAResponse;
import za.co.wirecard.channel.backoffice.exceptions.GenericException;
import za.co.wirecard.channel.backoffice.repositories.BackOfficeUserRepository;
import za.co.wirecard.channel.backoffice.services.GoogleAuthenticatorService;
import za.co.wirecard.channel.backoffice.services.S3BucketService;
import za.co.wirecard.channel.backoffice.services.TwoFAService;
import za.co.wirecard.channel.backoffice.services.UserVerifyService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;

@RestController
@CrossOrigin
@RequestMapping(value = "/channel-back-office/api/v1/unverified-user")
public class UserVerifyController {

    private final TwoFAService twoFAService;

    public UserVerifyController(TwoFAService twoFAService, UserVerifyService userVerifyService) {
        this.twoFAService = twoFAService;
        this.userVerifyService = userVerifyService;
    }

    private final UserVerifyService userVerifyService;
    private static final Logger logger = LogManager.getLogger(UserVerifyService.class);

    @GetMapping("/{id}")
    public ResponseEntity<?> getUnverifiedUser(@PathVariable String id, HttpServletRequest request, HttpSession session) {
        return ResponseEntity.ok(userVerifyService.getUnverifiedUser(id, session));
    }

    @PreAuthorize("@sessionSecurityEvaluator.hasSessionToAuthorityHttpSession(#httpSession, 'USER_VERIFY')")
    @PutMapping("/{id}")
    public void verifyUser(@PathVariable long id, @RequestBody PlatformVerifyUser platformVerifyUser, HttpServletRequest servletRequest, HttpSession httpSession) {
        try {
            userVerifyService.verifyUser(id, platformVerifyUser, servletRequest);
        } catch (RestClientResponseException e) {
            throw new GenericException("Invalid Credentials", HttpStatus.UNAUTHORIZED, "Invalid Username/Password combination");
        }
        // return twoFAService.createTwoFactorAuth(id, platformVerifyUser.getUsername());
    }

}
