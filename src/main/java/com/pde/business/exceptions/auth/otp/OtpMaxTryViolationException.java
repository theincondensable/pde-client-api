package com.pde.business.exceptions.auth.otp;

import com.pde.global.exceptions.BusinessException;
import org.springframework.http.HttpStatus;

/**
 * @author abbas
 */
public class OtpMaxTryViolationException extends BusinessException {

    public OtpMaxTryViolationException(String className, String inactiveUntil) {
        super(new BusinessException(
                "otp.code.max.try.violated",
                new Object[]{inactiveUntil},
                HttpStatus.FORBIDDEN,
                className
        ));
    }

}
