package com.pde.business.exceptions.auth;

import com.pde.global.exceptions.BusinessException;
import org.springframework.http.HttpStatus;

/**
 * @author abbas
 */
public class NewPasswordSameAsOldPasswordException extends BusinessException {

    public NewPasswordSameAsOldPasswordException(String className) {
        super(
                new BusinessException(
                        "new.password.and.old.password.the.same",
                        null,
                        HttpStatus.BAD_REQUEST,
                        className
                )
        );
    }

}
