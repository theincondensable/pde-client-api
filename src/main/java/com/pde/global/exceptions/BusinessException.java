package com.pde.global.exceptions;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import java.util.Locale;

/**
 * @author abbas
 */
@Getter
public class BusinessException extends RuntimeException {
    protected static final Logger logger = LoggerFactory.getLogger(BusinessException.class);

    private final String message;
    private final Object[] args;
    private final Locale locale;
    private final int httpStatusCode;
    private final String className;

    public BusinessException(String message, Object[] args, HttpStatus httpStatusCode, String className) {
        this.message = message;
        this.args = args;
        this.locale = Locale.getDefault();
        this.httpStatusCode = httpStatusCode.value();
        this.className = className;
    }

    public BusinessException(BusinessException e) {
        this.message = e.getMessage();
        this.args = e.getArgs();
        this.locale = e.getLocale();
        this.httpStatusCode = e.getHttpStatusCode();
        this.className = e.getClassName();
    }
}
