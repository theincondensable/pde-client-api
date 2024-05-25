package com.pde.business.exceptions.auth.otp;

import com.pde.global.exceptions.BusinessException;
import org.springframework.http.HttpStatus;

/**
 * @author abbas
 */
public class OtpMismatchException extends BusinessException {

    public OtpMismatchException(String className, String token) {
        super(new BusinessException(
                "otp.code.mismatch",
                new Object[]{token},
                HttpStatus.FORBIDDEN,
                className
        ));
    }

}
