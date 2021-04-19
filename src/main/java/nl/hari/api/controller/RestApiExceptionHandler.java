package nl.hari.api.controller;

import nl.hari.api.model.CustomRuntimeException;
import nl.hari.api.model.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    protected  ResponseEntity<Object> handleAllErrors(Exception ex){
        ErrorMessage errorResponse = defaultErrorMsg();
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CustomRuntimeException.class)
    protected ResponseEntity<Object> handleSpecificErrorForDemo(CustomRuntimeException cre){
        ErrorMessage errorMessage = extractToErrorMsg(cre);
        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private static ErrorMessage extractToErrorMsg(CustomRuntimeException cre){
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setErrorCode(cre.getErrorCode());
        errorMessage.setErrorDescription(cre.getErrorDescription());
        return errorMessage;
    }

    private static ErrorMessage defaultErrorMsg(){
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setErrorCode(500);
        errorMessage.setErrorDescription("This is a generic exception thrown from RestApiExceptionHandler class");
        return errorMessage;
    }


}
