package com.pde.business.exceptions.customer;

import com.pde.global.exceptions.BusinessException;
import org.springframework.http.HttpStatus;

/**
 * @author abbas
 */
public class CompanyDuplicateNationalRegistrationCodeException extends BusinessException {

    public CompanyDuplicateNationalRegistrationCodeException(String className, String nationalRegistrationCode) {
        super(
                "company.duplicate.national.registration.code",
                new Object[]{nationalRegistrationCode},
                HttpStatus.BAD_REQUEST,
                className
        );
    }

}
