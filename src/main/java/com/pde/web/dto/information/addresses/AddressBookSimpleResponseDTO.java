package com.pde.web.dto.information.addresses;

import com.pde.business.model.information.AddressBook;
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
public class AddressBookSimpleResponseDTO {
    private Long addressBookId;
    private String name;
    private String country;
    private String city;
    private String addressLine;

    public static AddressBookSimpleResponseDTO fromAddressBook(AddressBook addressBook) {
        AddressBookSimpleResponseDTO dto = new AddressBookSimpleResponseDTO();
        dto.setAddressBookId(addressBook.getId());
        dto.setName(addressBook.getName());
        dto.setCountry(addressBook.getCountry());
        dto.setCity(addressBook.getCity());
        dto.setAddressLine(addressBook.getAddressLine());
        return dto;
    }
}
