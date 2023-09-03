package za.co.wirecard.channel.backoffice.exceptions;

import org.springframework.http.HttpStatus;

public class MerchantAddressException extends GenericException {
    public MerchantAddressException(String reason, HttpStatus httpStatus, String response) {
        super(reason, httpStatus, response);
    }
}
