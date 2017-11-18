package com.goeuro.bus.route.rest;

import com.goeuro.bus.route.dto.ErrorMessageDTO;
import com.goeuro.bus.route.exception.ApplicationInitException;
import com.goeuro.bus.route.exception.InternalBaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;
import java.util.Locale;

@ControllerAdvice
public class InternalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(InternalExceptionHandler.class);

    @Autowired
    private MessageSource msgSource;
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<ErrorMessageDTO> processValidationError(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        FieldError error = result.getFieldError();
        return new ResponseEntity<>(processFieldError(error), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<ErrorMessageDTO> processValidationError(MethodArgumentTypeMismatchException ex) {
        return new ResponseEntity<>(processAttributeError(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<ErrorMessageDTO> constraintViolationException(ConstraintViolationException ex) {
        String message = ex.getConstraintViolations().iterator().next().getMessage();
        return new ResponseEntity<>(processAttributeError(message), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ApplicationInitException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<ErrorMessageDTO> processEntityNotExistError(InternalBaseException e)
    {
        ErrorMessageDTO localizedMessage = getLocalizedMessage(e);
        return new ResponseEntity<>(localizedMessage, HttpStatus.BAD_REQUEST);
    }

    private ErrorMessageDTO processAttributeError(String messageStr) {
        ErrorMessageDTO message = null;
        if (messageStr != null) {
            Locale currentLocale = LocaleContextHolder.getLocale();
            String msg = msgSource.getMessage(messageStr, null, currentLocale);
            message = new ErrorMessageDTO(msg);
        }
        return message;
    }

    private ErrorMessageDTO processFieldError(FieldError error) {
        ErrorMessageDTO message = null;
        if (error != null) {
            Locale currentLocale = LocaleContextHolder.getLocale();
            String msg = msgSource.getMessage(error.getDefaultMessage(), null, currentLocale);
            message = new ErrorMessageDTO(msg);
        }
        return message;
    }

    private ErrorMessageDTO getLocalizedMessage(InternalBaseException ex) {
        ErrorMessageDTO message = null;
        if (ex != null) {
            Locale currentLocale = LocaleContextHolder.getLocale();
            String msg = msgSource.getMessage(ex.getErrorMessageProperty(), null, currentLocale);
            message = new ErrorMessageDTO(msg);
        }
        return message;
    }

}
