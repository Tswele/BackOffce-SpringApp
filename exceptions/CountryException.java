package za.co.wirecard.channel.backoffice.exceptions;

import org.springframework.http.HttpStatus;

public class CountryException extends GenericException {

    public CountryException(String reason, HttpStatus httpStatus, String response) {
        super(reason, httpStatus, response);
    }
}
