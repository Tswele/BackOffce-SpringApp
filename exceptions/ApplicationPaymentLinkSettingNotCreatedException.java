package za.co.wirecard.channel.backoffice.exceptions;

import org.springframework.http.HttpStatus;

public class ApplicationPaymentLinkSettingNotCreatedException extends GroupException {
    public ApplicationPaymentLinkSettingNotCreatedException(String reason, HttpStatus httpStatus, String response) {
        super(reason, httpStatus, response);
    }
}
