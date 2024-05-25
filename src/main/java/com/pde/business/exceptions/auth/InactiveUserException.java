package com.pde.business.exceptions.auth;

import com.pde.global.exceptions.BusinessException;
import org.springframework.http.HttpStatus;

/**
 * @author abbas
 */
public class InactiveUserException extends BusinessException {
    public InactiveUserException(String className) {
        super(new BusinessException(
                "user.not.active",
                null,
                HttpStatus.FORBIDDEN,
                className
        ));
    }
}
