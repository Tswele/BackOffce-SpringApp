package za.co.wirecard.channel.backoffice.exceptions;

import org.springframework.http.HttpStatus;

public class ApplicationPaymentLinkSettingNotFoundException extends GenericException{
    public ApplicationPaymentLinkSettingNotFoundException(String reason, HttpStatus httpStatus, String response) {
        super(reason, httpStatus, response);
    }
}
