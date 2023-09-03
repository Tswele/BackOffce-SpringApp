package za.co.wirecard.channel.backoffice.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ExceptionExtender extends GenericException {

    public ExceptionExtender(String reason, HttpStatus httpStatus, String response) {
        super(reason, httpStatus, response);
    }

}