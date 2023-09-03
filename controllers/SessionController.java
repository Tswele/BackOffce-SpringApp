package za.co.wirecard.channel.backoffice.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientResponseException;
import za.co.wirecard.channel.backoffice.config.JwtSession;
import za.co.wirecard.channel.backoffice.config.VerifyUserSession;
import za.co.wirecard.channel.backoffice.dto.models.requests.CheckAuthorities;
import za.co.wirecard.channel.backoffice.exceptions.GenericException;
import za.co.wirecard.channel.backoffice.models.Token;
import za.co.wirecard.channel.backoffice.models.TokenVerify;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/channel-back-office/api/v1/session")
public class SessionController {

    private static final Logger logger = LogManager.getLogger(SessionController.class);

    @GetMapping("/retrieve")
    ResponseEntity<Token> retrieveSession(HttpSession session) {
        logger.info("Attempting to return session for session id | " + session.getId());
        try {
            JwtSession jwtSession = (JwtSession) session.getAttribute("token");
            if (jwtSession == null) {
                throw new GenericException("Could not find session", HttpStatus.FORBIDDEN, "No session exists with id | " + session.getId());
            }
            logger.info("Session found for id | " + session.getId());
            Token token = jwtSession.getToken();
            return ResponseEntity.ok(token);
        } catch (RestClientResponseException e) {
            throw new GenericException("Could not find session", HttpStatus.FORBIDDEN, "No session exists with id | " + session.getId());
        }
    }

    @GetMapping("/retrieve-verify")
    ResponseEntity<TokenVerify> retrieveSessionVerify(HttpSession session) {
        try {
            VerifyUserSession verifyUserSession = (VerifyUserSession) session.getAttribute("tokenVerify");
            if (verifyUserSession == null) {
                throw new GenericException("Could not find session", HttpStatus.FORBIDDEN, "No session exists with id | " + session.getId());
            }
            TokenVerify tokenVerify = verifyUserSession.getTokenVerify();
            return ResponseEntity.ok(tokenVerify);
        } catch (RestClientResponseException e) {
            throw new GenericException("Could not find session", HttpStatus.FORBIDDEN, "No session exists with id | " + session.getId());
        }
    }

    @PostMapping("/check-authorities")
    ResponseEntity<?> checkSessionAuthorities(@RequestBody @Valid CheckAuthorities checkAuthorities, HttpSession session) {
        try {
            JwtSession jwtSession = (JwtSession) session.getAttribute("token");
            if (jwtSession == null) {
                throw new GenericException("Could not find session", HttpStatus.FORBIDDEN, "No session exists with id | " + session.getId());
            }
            Token token = jwtSession.getToken();
            if (!isRoutePermitted(checkAuthorities.getRoute(), token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (RestClientResponseException e) {
            throw new GenericException("Could not find session", HttpStatus.FORBIDDEN, "No session exists with id | " + session.getId());
        }
    }

    @GetMapping("/logout")
    ResponseEntity<?> logout(HttpSession session) {
        try {
            session.removeAttribute("token");
            session.invalidate();
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (RestClientResponseException e) {
            throw new GenericException("Could not find session", HttpStatus.FORBIDDEN, "No session exists with id | " + session.getId());
        }
    }

    public boolean isRoutePermitted(String route, Token token) {
        JWT jwt = new JWT();
        DecodedJWT decodedJWT = jwt.decodeJwt(token.getAccess_token());
        List<String> authorities = decodedJWT.getClaim("authorities")
                .asList(String.class);
        switch (route) {
            case "home": {
                return authorities.size() > 0;
            }
            case "users": {
             return authorities.stream().anyMatch(s -> s.equalsIgnoreCase("USERS_VIEW"));
            }
            case "groups": {
                return authorities.stream().anyMatch(s -> s.equalsIgnoreCase("USERGROUPS_VIEW"));
            }
            case "transaction-manager": {
                return authorities.stream().anyMatch(s -> s.equalsIgnoreCase("TRANSACTION_VIEW"));
            }
            case "batch-manager": {
                return authorities.stream().anyMatch(s -> s.equalsIgnoreCase("BATCH_TRANSACTION_VIEW"));
            }
            case "client": {
                return authorities.stream().anyMatch(s -> s.equalsIgnoreCase("MERCHANT_VIEW"));
            }
            case "document": {
                return authorities.stream().anyMatch(s -> s.equalsIgnoreCase("CLIENT_DOCUMENT_VIEW"));
            }
            case "rate-structures": {
                return authorities.stream().anyMatch(s -> s.equalsIgnoreCase("RATE_STRUCTURES_VIEW"));
            }
            case "product-default": {
                return authorities.stream().anyMatch(s -> s.equalsIgnoreCase("PRODUCT_DEFAULT_VIEW"));
            }
            case "billing": {
                return authorities.stream().anyMatch(s -> s.equalsIgnoreCase("BILLING_VIEW"));
            }
            default: return false;
        }
    }

}
