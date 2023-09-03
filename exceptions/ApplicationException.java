package za.co.wirecard.channel.backoffice.exceptions;

import org.springframework.http.HttpStatus;

public class ApplicationException extends GenericException {
    public ApplicationException(String reason, HttpStatus httpStatus, String response) {
        super(reason, httpStatus, response);
    }
}
