package za.co.wirecard.channel.backoffice.exceptions;

import org.springframework.http.HttpStatus;

public class ContactException extends GenericException {
    public ContactException(String reason, HttpStatus httpStatus, String response) {
        super(reason, httpStatus, response);
    }
}
