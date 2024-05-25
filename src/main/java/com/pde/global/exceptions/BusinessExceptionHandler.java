package com.pde.global.exceptions;

import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

/**
 * @author abbas
 */
@RestControllerAdvice
public class BusinessExceptionHandler {

    private final MessageSource messageSource;

    public BusinessExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<String> handleBusinessException(BusinessException ex) {
        return ResponseEntity.status(ex.getHttpStatusCode()).body(messageSource.getMessage(ex.getMessage(), ex.getArgs(), Locale.getDefault()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseRequestFieldsError> validationException(MethodArgumentNotValidException ex) {
        List<FieldError> fieldErrors = ex.getFieldErrors();
        List<RequestFieldsError> errors = new ArrayList<>(fieldErrors.size());
        for (FieldError fieldError : fieldErrors) {
            String field = fieldError.getField();
            if (field.contains(".")) field = field.split("\\.")[1];
            errors.add(new RequestFieldsError(fieldError.getDefaultMessage(), field));
        }
        BaseRequestFieldsError fieldsError = new BaseRequestFieldsError(errors);
        return ResponseEntity.of(Optional.of(fieldsError));
    }

}
