package za.co.wirecard.channel.backoffice.exceptions;

import org.springframework.http.HttpStatus;

public class MerchantException extends GenericException {
    public MerchantException(String reason, HttpStatus httpStatus, String response) {
        super(reason, httpStatus, response);
    }
}
