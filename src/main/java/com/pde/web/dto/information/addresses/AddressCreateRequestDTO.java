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
public class AddressCreateRequestDTO {
    private String country;
    private String state;
    private String city;
    private String addressLine;

    private String telephoneNumber;
    private String extension;

    private String postalCode;
}
