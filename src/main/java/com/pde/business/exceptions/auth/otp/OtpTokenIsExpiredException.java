package com.pde.business.exceptions.auth.otp;

import com.pde.global.exceptions.BusinessException;
import org.springframework.http.HttpStatus;

/**
 * @author abbas
 */
public class OtpTokenIsExpiredException extends BusinessException {

    public OtpTokenIsExpiredException(String className) {
        super(new BusinessException(
                "otp.code.is.expired",
                null,
                HttpStatus.FORBIDDEN,
                className
        ));
    }

}
