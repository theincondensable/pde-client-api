package com.pde.business.exceptions.auth.otp;

import com.pde.global.exceptions.BusinessException;
import org.springframework.http.HttpStatus;

/**
 * @author abbas
 */
public class OtpIsAlreadySentException extends BusinessException {
    public OtpIsAlreadySentException(String className) {
        super(new BusinessException(
                "otp.is.already.sent",
                null,
                HttpStatus.BAD_REQUEST,
                className
        ));
    }
}
