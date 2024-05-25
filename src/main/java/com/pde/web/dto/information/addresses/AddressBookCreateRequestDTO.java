package com.pde.web.dto.information.addresses;

import jakarta.validation.Valid;
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
public class AddressBookCreateRequestDTO {

    @Valid
    private AddressCreateRequestDTO address;

    @Valid
    private ContactCreateRequestDTO contact;

}
