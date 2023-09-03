package za.co.wirecard.channel.backoffice.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;

@Component(value = "sessionSecurityEvaluator")
public class SessionSecurityEvaluator {

    private static final Logger logger = LogManager.getLogger(SessionSecurityEvaluator.class);

    public boolean hasSessionToAuthority(HttpSession httpSession, HttpServletRequest servletRequest, String permission) {
        logger.info("Checking if user has " + permission + " permission");
        try {
            // JwtSession jwtSession = (JwtSession) httpSession.getAttribute("token");
//            return jwtSession.getToken()
//                    .getPermissions()
//                    .stream().anyMatch(authority -> authority.getAuthority().equalsIgnoreCase(permission));
            String bearerToken = servletRequest.getHeader("Authorization");
            JWT jwt = new JWT();
            DecodedJWT decodedJWT = jwt.decodeJwt(bearerToken.replace("Bearer ", ""));
            return decodedJWT.getClaim("authorities")
                    .asList(String.class)
                    .stream()
                    .anyMatch(authority -> authority.equalsIgnoreCase(permission));
        } catch (Exception e) {
            logger.error("Error occurred | " + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }

    public boolean hasSessionToAuthorityHttpSession (HttpSession httpSession, String permission) {
        logger.info("Checking if user has " + permission + " permission");
        try {
            VerifyUserSession jwtSession = (VerifyUserSession) httpSession.getAttribute("tokenVerify");
            logger.info("ZZZ | " + jwtSession);
            JWT jwt = new JWT();
            DecodedJWT decodedJWT = jwt.decodeJwt(jwtSession.getTokenVerify().getAccess_token().replace("Bearer ", ""));
            return decodedJWT.getClaim("authorities")
                    .asList(String.class)
                    .stream()
                    .anyMatch(authority -> authority.equalsIgnoreCase(permission));
        } catch (Exception e) {
            logger.error("Error occurred | " + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }

}
