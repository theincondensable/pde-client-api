package com.pde.business.exceptions.customer;

import com.pde.global.exceptions.BusinessException;
import org.springframework.http.HttpStatus;

/**
 * @author abbas
 */
public class PersonDuplicateNationalIdException extends BusinessException {

    public PersonDuplicateNationalIdException(String className, String nationalId) {
        super(
                "person.duplicate.national.id",
                new Object[]{nationalId},
                HttpStatus.BAD_REQUEST,
                className
        );
    }

}
