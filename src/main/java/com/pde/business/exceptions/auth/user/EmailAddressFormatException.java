package com.pde.business.exceptions.auth.user;

import com.pde.global.exceptions.BusinessException;
import org.springframework.http.HttpStatus;

/**
 * @author abbas
 */
public class EmailAddressFormatException extends BusinessException {
    public EmailAddressFormatException(String className, String emailAddress) {
        super(new BusinessException(
                "mail.format.incorrect",
                new Object[]{emailAddress},
                HttpStatus.BAD_REQUEST,
                className
        ));
    }
}
