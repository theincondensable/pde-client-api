package com.pde.web.dto.auth.register;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
public class BaseUserRegisterRequestDTO {

    @Pattern(regexp = "^[ ء-ی]*", message = "{input.farsi.first.name.incorrect}")
    private String farsiFirstName;

    @Pattern(regexp = "^[ ء-ی]*", message = "{input.farsi.last.name.incorrect}")
    private String farsiLastName;

    @NotBlank(message = "{input.english.first.name.is.null}")
    @Pattern(regexp = "^[a-zA-Z]*", message = "{input.english.first.name.incorrect}")
    private String englishFirstName;

    @NotBlank(message = "{input.english.last.name.is.null}")
    @Pattern(regexp = "^[a-zA-Z]*", message = "{input.english.last.name.incorrect}")
    private String englishLastName;

    @Email(message = "{input.main.email.format.incorrect}")
    @NotBlank(message = "{input.main.email.is.null}")
    private String mainEmailAddress;

    @Email(message = "{input.secondary.email.format.incorrect}")
    private String secondaryEmailAddress;

    @NotBlank(message = "{input.phone.number.email.is.null}")
    private String mobilePhoneNumber;

    @NotBlank(message = "{input.password.is.null}")
    @Size(min = 8, message = "{input.password.length}")
    private String password;
    @NotBlank(message = "{input.confirm.password.is.null}")
    @Size(min = 8, message = "{input.confirm.password.length}")
    private String confirmPassword;

    @NotBlank(message = "{input.country.is.null}")
    private String country;

    @Override
    public String toString() {
        return "{" +
                "farsiFirstName='" + farsiFirstName + '\'' +
                ", farsiLastName='" + farsiLastName + '\'' +
                ", englishFirstName='" + englishFirstName + '\'' +
                ", englishLastName='" + englishLastName + '\'' +
                ", mainEmailAddress='" + mainEmailAddress + '\'' +
                ", secondaryEmailAddress='" + secondaryEmailAddress + '\'' +
                ", mobilePhoneNumber='" + mobilePhoneNumber + '\'' +
                ", password='****" + '\'' +
                ", confirmPassword='****" + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
