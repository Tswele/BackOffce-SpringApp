package za.co.wirecard.channel.backoffice.exceptions;

import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;

@Getter
public class GenericException extends RuntimeException {

    private final String reason;
    private final HttpStatus httpStatus;
    private final String response;

    private static final Logger logger = LogManager.getLogger(GenericException.class);

    public GenericException(String reason, HttpStatus httpStatus, String response) {
        super(reason + " " + httpStatus.toString() + " " + response);
        this.reason = reason;
        this.httpStatus = httpStatus;
        this.response = response;
        logger.info("Status " + this.httpStatus + " Response " + this.response + " reason " + this.reason);
    }

}
