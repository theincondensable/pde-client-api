package com.pde.business.exceptions.auth.user;

import com.pde.global.exceptions.BusinessException;
import org.springframework.http.HttpStatus;

/**
 * @author abbas
 */
public class EmailDuplicateException extends BusinessException {

    public EmailDuplicateException(String className, String email) {
        super(new BusinessException(
                "user.duplicate.email",
                new Object[]{email},
                HttpStatus.BAD_REQUEST,
                className
        ));
    }

}
