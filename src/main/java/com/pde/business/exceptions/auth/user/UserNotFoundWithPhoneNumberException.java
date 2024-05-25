package com.pde.business.exceptions.auth.user;

import com.pde.global.exceptions.BusinessException;
import org.springframework.http.HttpStatus;

/**
 * @author abbas
 */
public class UserNotFoundWithPhoneNumberException extends BusinessException {

    public UserNotFoundWithPhoneNumberException(String className, String phoneNumber) {
        super(new BusinessException(
                "user.not.found.with.phone.number",
                new Object[]{phoneNumber},
                HttpStatus.NOT_FOUND,
                className
        ));
    }

}