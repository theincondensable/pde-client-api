package com.pde.business.exceptions.auth.user;

import com.pde.global.exceptions.BusinessException;
import org.springframework.http.HttpStatus;

/**
 * @author abbas
 */
public class UserNotFoundWithEmailException extends BusinessException {

    public UserNotFoundWithEmailException(String className, String email) {
        super(new BusinessException(
                "user.not.found.with.email",
                new Object[]{email},
                HttpStatus.NOT_FOUND,
                className
        ));
    }

}