package za.co.wirecard.channel.backoffice.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class EmailAlreadyInUseException extends GenericException {

    public EmailAlreadyInUseException(String reason, HttpStatus httpStatus, String response) {
        super(reason, httpStatus, response);
    }

}
