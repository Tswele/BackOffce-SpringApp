package za.co.wirecard.channel.backoffice.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import za.co.wirecard.channel.backoffice.exceptions.GenericException;
import za.co.wirecard.channel.backoffice.services.TwoFAService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service
public class Utils {

    private static final Logger logger = LogManager.getLogger(Utils.class);

    public static final String SLACK_NOTIFICATION_QUEUE_NAME = "slackNotifications";
    public static final String SMS_NOTIFICATION_QUEUE_NAME = "smsNotifications";
    public static final String EMAIL_NOTIFICATION_QUEUE_NAME = "emailNotifications";
    public static final String MERCHANT_NOTIFICATION_QUEUE_NAME = "merchantEmailNotifications";
    public static final String BILLING_RUN_QUEUE_NAME = "billingRuns";
    public static final String OTP_HTML_ID = "otp-input";
    public static final String OTP_PARENT_HTML_ID = "otp-parent";

    public static final String CRUD_GET = "GET";
    public static final String CRUD_PUT = "PUT";
    public static final String CRUD_POST = "POST";
    public static final String CRUD_DELETE = "DELETE";

    public static final String BACK_OFFICE_ROLE_CREDIT_CONTROLLER = "creditController";
    public static final String BACK_OFFICE_ROLE_ACCOUNT_MANAGER = "accountManager";
    public static final String BACK_OFFICE_ROLE_SALES_PERSON = "salesPerson";

    public static final String TRANSACTION_VIEW = "TRANSACTION_VIEW";

    public String MERCHANT_UID = "MERCHANT_UID";
    public String APPLICATION_UID = "APPLICATION_UID";
    public String CLIENT_NAME = "CLIENT_NAME";
    public String ACCOUNT_NO = "ACCOUNT_NO";

    /*STATE MANAGEMENT*/
    public static final String MERCHANT_STATUS_CANCELLED_CODE = "CANCELLED";
    public static final String MERCHANT_STATUS_SUSPENDED_CODE = "SUSPENDED";
    public static final String MERCHANT_STATUS_ACTIVE_CODE = "ACTIVE";
    public static final String MERCHANT_STATUS_PENDING_CODE = "PENDING";
    public static final String MERCHANT_STATUS_DEVELOPING_CODE = "DEVELOPING";
    public static HttpHeaders getPlatformHeaders() {
        HttpSession session = getHttpSession();
        JwtSession jwtSession = (JwtSession) session.getAttribute("token");
        logger.info("JWT_SESSION | " + jwtSession.getToken().getAccess_token());
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(jwtSession.getToken().getAccess_token());
        return headers;
    }

    public static HttpSession getHttpSession() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attr.getRequest().getSession(true);
    }

    public static Long getUserFromSession(HttpServletRequest servletRequest) {
        String accessToken = servletRequest.getHeader(HttpHeaders.AUTHORIZATION).replace("Bearer ", "");
        if (accessToken == null) {
            throw new GenericException("Token information service was not passed proper parameters", HttpStatus.BAD_REQUEST, "Token derived object is null");
        }
        JWT jwt = new JWT();
        DecodedJWT decodedJWT = jwt.decodeJwt(accessToken);
        Long userId = decodedJWT.getClaims().get("userId") != null ? decodedJWT.getClaims().get("userId").asLong() : null;
        return userId;
    }

    public static String getLevelFromReferalUrl(HttpServletRequest servletRequest) {
        String referer = servletRequest.getHeader(HttpHeaders.REFERER);
        String level = null;
        if (referer == null) {
            throw new GenericException("Could not determine referer for Level", HttpStatus.BAD_REQUEST, "Referer derived object is null");
        }
        if(referer.toUpperCase().contains("CLIENT"))
        {
            level = "MERCHANT";
        }
        return level;
    }

}
