package com.pde.business.service.auth;

/**
 * @author abbas
 */
public final class TokensEnum {

    public static final int JWT_TOKEN_EXPIRES_IN_MINUTES = 60;
    public static final int JWT_TOKEN_WITH_REMEMBER_ME_EXPIRES_IN_MINUTES = 20160; // TWO WEEKS
    public static final int OTP_EXPIRES_IN_MINUTES = 2;
    public static final int OTP_MAX_TRY = 2;
    public static final int OTP_SUSPENSION_IN_MINUTES = 30;


    private TokensEnum() {

    }

}
