package za.co.wirecard.channel.backoffice.exceptions;

import org.springframework.http.HttpStatus;

public class XeroAuthorizationException extends GenericException{
    public XeroAuthorizationException(String reason, HttpStatus httpStatus, String response) {
        super(reason, httpStatus, response);
    }
}
