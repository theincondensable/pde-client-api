package com.pde.web.dto.auth.register;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * @author abbas
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonUserRegisterRequestDTO {

    @Valid
    private BaseUserRegisterRequestDTO user;

    @NotBlank(message = "{input.national.id.is.null}")
    @Pattern(regexp = "[0-9]*", message = "{input.national.id.incorrect}")
    private String nationalId;

    @NotNull(message = "{input.birthdate.is.null}")
    private Date birthDate;

    @Override
    public String toString() {
        return "{" +
                "user=" + user.toString() +
                ", nationalId='" + nationalId + '\'' +
                '}';
    }
}
