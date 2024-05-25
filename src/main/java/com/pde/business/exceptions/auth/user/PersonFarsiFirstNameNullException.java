package com.pde.business.exceptions.auth.user;

import com.pde.global.exceptions.BusinessException;
import org.springframework.http.HttpStatus;

/**
 * @author abbas
 */
public class PersonFarsiFirstNameNullException extends BusinessException {
    public PersonFarsiFirstNameNullException(String className) {
        super(new BusinessException(
                "person.farsi.first.name.null",
                null,
                HttpStatus.BAD_REQUEST,
                className
        ));
    }
}
