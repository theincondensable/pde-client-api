package com.pde.business.exceptions.auth.user;

import com.pde.global.exceptions.BusinessException;
import org.springframework.http.HttpStatus;

/**
 * @author abbas
 */
public class PhoneNumberDuplicateException extends BusinessException {

    public PhoneNumberDuplicateException(String className, String phoneNumber) {
        super(new BusinessException(
                "user.duplicate.phone.number",
                new Object[]{phoneNumber},
                HttpStatus.BAD_REQUEST,
                className
        ));
    }

}
