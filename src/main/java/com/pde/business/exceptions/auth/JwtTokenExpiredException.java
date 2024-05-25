package com.pde.business.exceptions.auth;

import com.pde.global.exceptions.BusinessException;
import org.springframework.http.HttpStatus;

/**
 * @author abbas
 */
public class JwtTokenExpiredException extends BusinessException {
    public JwtTokenExpiredException(String className) {
        super(new BusinessException(
                "jwt.token.is.expired", null, HttpStatus.UNAUTHORIZED, className
        ));
    }
}
