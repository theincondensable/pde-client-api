package com.pde.business.exceptions.auth.user;

import com.pde.global.exceptions.BusinessException;
import org.springframework.http.HttpStatus;

/**
 * @author abbas
 */
public class InvalidPhoneNumberException extends BusinessException {
    public InvalidPhoneNumberException(String className, String phoneNumber) {
        super(new BusinessException(
                "phone.number.format.incorrect",
                new Object[]{phoneNumber},
                HttpStatus.BAD_REQUEST,
                className
        ));
    }
}
