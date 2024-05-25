package com.pde.business.exceptions.auth;

import com.pde.global.exceptions.BusinessException;
import org.springframework.http.HttpStatus;

/**
 * @author abbas
 */
public class PasswordAndConfirmPasswordMismatchException extends BusinessException {

    public PasswordAndConfirmPasswordMismatchException(String className) {
        super(
                new BusinessException(
                        "password.and.confirm.password.mismatch",
                        null,
                        HttpStatus.BAD_REQUEST,
                        className
                )
        );
    }

}
