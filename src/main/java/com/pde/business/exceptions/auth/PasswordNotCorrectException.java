package com.pde.business.exceptions.auth;

import com.pde.global.exceptions.BusinessException;
import org.springframework.http.HttpStatus;

/**
 * @author abbas
 */
public class PasswordNotCorrectException extends BusinessException {
    public PasswordNotCorrectException(String className) {
        super(new BusinessException(
                "entered.password.mismatch",
                null,
                HttpStatus.FORBIDDEN,
                className
        ));
    }
}
