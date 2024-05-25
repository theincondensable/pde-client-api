package com.pde.web.dto.auth.register;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
public class CompanyUserRegisterRequestDTO {

    @Valid
    private BaseUserRegisterRequestDTO companyContactPerson;

    @NotBlank(message = "{input.farsi.company.name.is.null}")
    @Pattern(regexp = "^[ ء-ی]*")
    private String farsiCompanyName;
    @NotBlank(message = "{input.english.company.name.is.null}")
    @Pattern(regexp = "^[a-zA-Z ]*")
    private String englishCompanyName;

    @NotBlank(message = "{input.national.registration.code.is.null}")
    private String nationalRegistrationCode;

    @Override
    public String toString() {
        return "{" + "user=" + companyContactPerson + ", farsiCompanyName='" + farsiCompanyName + '\'' + ", englishCompanyName='" + englishCompanyName + '\'' + ", nationalRegistrationCode='" + nationalRegistrationCode + '\'' + '}';
    }
}
