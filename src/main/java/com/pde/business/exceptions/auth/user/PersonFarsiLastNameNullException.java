package com.pde.business.exceptions.auth.user;

import com.pde.global.exceptions.BusinessException;
import org.springframework.http.HttpStatus;

/**
 * @author abbas
 */
public class PersonFarsiLastNameNullException extends BusinessException {
    public PersonFarsiLastNameNullException(String className) {
        super(new BusinessException(
                "person.farsi.last.name.null",
                null,
                HttpStatus.BAD_REQUEST,
                className
        ));
    }
}
