package com.pde.business.exceptions.auth.otp;

import com.pde.global.exceptions.BusinessException;
import org.springframework.http.HttpStatus;

/**
 * @author abbas
 */
public class OtpNotRequestedException extends BusinessException {
    public OtpNotRequestedException(String className) {
        super(new BusinessException(
                "otp.not.requested.yet",
                null,
                HttpStatus.BAD_REQUEST,
                className
        ));
    }
}
