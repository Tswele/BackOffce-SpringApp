package za.co.wirecard.channel.backoffice.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import za.co.wirecard.channel.backoffice.models.error.Errors;

import javax.servlet.http.HttpSession;

@Component
public class UtilityService {

    private HttpHeaders httpHeaders;
    private final Environment environment;
    private ObjectMapper objectMapper;

    private static final Logger logger = LogManager.getLogger(UtilityService.class);

    public UtilityService(Environment environment) {
        this.environment = environment;
    }

    public static String getToken() {
        HttpSession session = getHttpSession();
        JwtSession jwtSession = (JwtSession) session.getAttribute("token");
        logger.info("SESSION HEADERS | " + jwtSession.getToken().getAccess_token());
        if (jwtSession != null) {
            return jwtSession.getToken().getAccess_token();
        }
        return null;
    }


//    public String getProperty(ApplicationConstants key) {
//        String value = this.environment.getProperty(key.value());
//        if(null == value)   {
//            logger.info("Property not found | " + key);
//        }
//        return value;
//    }

    public HttpHeaders getHttpHeaders()    {
        if(null == httpHeaders)    {
            this.httpHeaders = new HttpHeaders();
        }
        this.httpHeaders.setBearerAuth(getToken());
        return this.httpHeaders;
    }

    public ObjectMapper getObjectMapper()   {
        if(null == this.objectMapper)   {
            this.objectMapper =  new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        }
        return this.objectMapper;
    }

    public String getReasonFromApiError(String errorResponse)  {
        try {
            return this.getObjectMapper().readValue(errorResponse, Errors.class).getErrorItems().get(0).getMessage();
        } catch (JsonProcessingException e) {
            return errorResponse;
        }
    }

    public static HttpSession getHttpSession() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attr.getRequest().getSession(true);
    }

}
