package com.pde.web.dto.auth.login;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

/**
 * @author abbas
 */
@Getter
@Setter
public class OtpValidationRequestDTO {

    private String phoneNumber;

    @NotNull(message = "{input.opt.code.is.null}")
    @Range(min = 1000, max = 9999, message = "{otp.code.length}")
    private Integer otpCode;

    @Override
    public String toString() {
        return "{" +
                "phoneNumber='" + phoneNumber + '\'' +
                ", otpCode='" + otpCode + '\'' +
                '}';
    }
}
