package za.co.wirecard.channel.backoffice.exceptions;

import org.springframework.http.HttpStatus;

public class GroupException extends GenericException {
    public GroupException(String reason, HttpStatus httpStatus, String response) {
        super(reason, httpStatus, response);
    }
}
