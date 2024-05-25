package com.pde.business.exceptions.auth.user;

import com.pde.global.exceptions.BusinessException;
import org.springframework.http.HttpStatus;

/**
 * @author abbas
 */
public class EmailMainAndSecondaryTheSameException extends BusinessException {
    public EmailMainAndSecondaryTheSameException(String className) {
        super(new BusinessException(
                "main.email.and.secondary.the.same",
                null,
                HttpStatus.FORBIDDEN,
                className
        ));
    }
}
