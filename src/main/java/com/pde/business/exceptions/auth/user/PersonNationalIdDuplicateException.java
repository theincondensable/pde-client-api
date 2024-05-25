package com.pde.business.exceptions.auth.user;

import com.pde.global.exceptions.BusinessException;
import org.springframework.http.HttpStatus;

/**
 * @author abbas
 */
public class PersonNationalIdDuplicateException extends BusinessException {
    public PersonNationalIdDuplicateException(String className, String nationalId) {
        super(
                new BusinessException(
                        "person.duplicate.national.id",
                        new Object[]{nationalId},
                        HttpStatus.BAD_REQUEST,
                        className
                )
        );
    }
}
