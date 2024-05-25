package com.pde.web.dto.auth;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <p>
 * For when the User wants to Login with Email Address.
 * in this case, we need the Password.
 * </p>
 *
 * @author abbas
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginWithEmailRequestDTO {

    @Email
    private String email;
    private String password;
    private Boolean rememberMe;

    @Override
    public String toString() {
        return "{" +
                "email='" + email + '\'' +
                ", password='****" + '\'' +
                ", rememberMe='" + rememberMe + '\'' +
                '}';
    }
}
