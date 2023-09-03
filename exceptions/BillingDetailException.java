package za.co.wirecard.channel.backoffice.exceptions;

import org.springframework.http.HttpStatus;

public class BillingDetailException extends GenericException {

    public BillingDetailException(String reason, HttpStatus httpStatus, String response) {
        super(reason, httpStatus, response);
    }
}
