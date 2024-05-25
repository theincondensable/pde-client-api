package com.pde.web.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author abbas
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordRequestDTO {

    @NotBlank(message = "{input.current.password.is.null}")
    @Size(min = 8, message = "{input.confirm.password.length}")
    private String currentPassword;

    @NotBlank(message = "{input.password.is.null}")
    @Size(min = 8, message = "{input.password.length}")
    private String password;
    @NotBlank(message = "{input.confirm.password.is.null}")
    @Size(min = 8, message = "{input.confirm.password.length}")
    private String confirmPassword;

}
