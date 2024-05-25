package com.pde.business.exceptions.auth.user;

import com.pde.global.exceptions.BusinessException;
import org.springframework.http.HttpStatus;

/**
 * @author abbas
 */
public class InvalidNationalIdException extends BusinessException {
    public InvalidNationalIdException(String className, String nationalId) {
        super(new BusinessException(
                "national.id.format.incorrect",
                new Object[]{nationalId},
                HttpStatus.BAD_REQUEST,
                className
        ));
    }
}
