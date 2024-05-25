package com.pde.web.dto.information.addresses;

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
public class ContactUpdateRequestDTO {
    private String englishFirstName;
    private String englishLastName;

    private String emailAddressMain;
    private String emailAddressSecondary;
    private String phoneNumber;

    private String about;
}
