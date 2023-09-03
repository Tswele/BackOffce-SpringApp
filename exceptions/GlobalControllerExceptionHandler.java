package za.co.wirecard.channel.backoffice.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import za.co.wirecard.channel.backoffice.models.error.ErrorItem;
import za.co.wirecard.channel.backoffice.models.error.Errors;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@ControllerAdvice
public class GlobalControllerExceptionHandler extends ResponseEntityExceptionHandler {

    public GlobalControllerExceptionHandler() {

    }

    @ExceptionHandler(EmailAlreadyInUseException.class)
    public ResponseEntity<Errors> handleCardInitializationException(EmailAlreadyInUseException emailAlreadyInUseException) {
        return ResponseEntity.status(emailAlreadyInUseException.getHttpStatus()).body(buildErrorMessage(emailAlreadyInUseException));
    }

    @ExceptionHandler(ApplicationPaymentLinkSettingNotCreatedException.class)
    public ResponseEntity<Errors> handleApplicationPaymentLinkSettingNotCreatedException(ApplicationPaymentLinkSettingNotCreatedException applicationPaymentLinkSettingNotCreatedException) {
        return ResponseEntity.status(applicationPaymentLinkSettingNotCreatedException.getHttpStatus()).body(buildErrorMessage(applicationPaymentLinkSettingNotCreatedException));
    }

    @ExceptionHandler(ApplicationPaymentLinkSettingNotFoundException.class)
    public ResponseEntity<Errors> handleApplicationPaymentLinkSettingNotFoundException(ApplicationPaymentLinkSettingNotFoundException applicationPaymentLinkSettingNotFoundException) {
        return ResponseEntity.status(applicationPaymentLinkSettingNotFoundException.getHttpStatus()).body(buildErrorMessage(applicationPaymentLinkSettingNotFoundException));
    }

    @ExceptionHandler(ExceptionExtender.class)
    public ResponseEntity<Errors> handleCardInitializationException(ExceptionExtender exceptionExtender) {
        return ResponseEntity.status(exceptionExtender.getHttpStatus()).body(buildErrorMessage(exceptionExtender));
    }

    @ExceptionHandler(GenericException.class)
    public ResponseEntity<Errors> handleGenericException(GenericException genericException) {
        return ResponseEntity.status(genericException.getHttpStatus()).body(buildErrorMessage(genericException));
    }

    @ExceptionHandler(XeroAuthorizationException.class)
    public ResponseEntity<Errors> handleXeroAuthorizationException(XeroAuthorizationException xeroAuthorizationException) {
        return ResponseEntity.status(xeroAuthorizationException.getHttpStatus()).body(buildErrorMessage(xeroAuthorizationException));
    }

    public Errors buildErrorMessage(GenericException genericException)  {
        logger.error(genericException.getReason(), genericException);
        List<ErrorItem> listOfErrorItems = new ArrayList<>();
        listOfErrorItems.add(new ErrorItem(genericException.getReason(), genericException.getResponse()));
        return new Errors(genericException.getHttpStatus().toString(), genericException.getReason(), listOfErrorItems);
    }



}

