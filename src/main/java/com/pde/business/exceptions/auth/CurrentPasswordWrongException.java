package com.pde.business.exceptions.auth;

import com.pde.global.exceptions.BusinessException;
import org.springframework.http.HttpStatus;

/**
 * @author abbas
 */
public class CurrentPasswordWrongException extends BusinessException {

    public CurrentPasswordWrongException(String className) {
        super(
                new BusinessException(
                        "current.password.incorrect",
                        null,
                        HttpStatus.BAD_REQUEST,
                        className
                )
        );
    }

}
