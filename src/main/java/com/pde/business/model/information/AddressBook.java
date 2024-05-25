package com.pde.business.model.information;

import com.pde.business.model.BaseEntity;
import com.pde.business.model.auth.User;
import com.pde.web.dto.information.addresses.AddressBookCreateRequestDTO;
import com.pde.web.dto.information.addresses.AddressBookUpdateRequestDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

/**
 * @author abbas
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressBook extends BaseEntity {

    private String country;
    private String state;
    private String city;
    private String addressLine;

    private String telephoneNumber;
    private String extension;

    private String postalCode;

    private String name;
    //    @Column(unique = true)
    private String emailAddressMain;
    //    @Column(unique = true)
    private String emailAddressSecondary;
    //    @Column(unique = true)
    private String phoneNumber;
    private String about;

    @ManyToMany
    @JoinTable(name = "userAddressBooks",
            joinColumns = {@JoinColumn(name = "FK_ADDRESS_BOOK")},
            inverseJoinColumns = {@JoinColumn(name = "FK_USER")})
    private Set<User> users = new HashSet<>();

    public static AddressBook createAddressBook(User user, AddressBookCreateRequestDTO req) {
        AddressBook addressBook = new AddressBook();
        addressBook.getUsers().add(user);
        addressBook.setName(req.getContact().getEnglishFirstName() + " " + req.getContact().getEnglishLastName());
        addressBook.setEmailAddressMain(req.getContact().getEmailAddressMain());
        addressBook.setEmailAddressSecondary(req.getContact().getEmailAddressSecondary());
        addressBook.setPhoneNumber(req.getContact().getPhoneNumber());
        addressBook.setAbout(req.getContact().getAbout());

        addressBook.setCountry(req.getAddress().getCountry());
        addressBook.setState(req.getAddress().getState());
        addressBook.setCity(req.getAddress().getCity());
        addressBook.setAddressLine(req.getAddress().getAddressLine());
        addressBook.setTelephoneNumber(req.getAddress().getTelephoneNumber());
        addressBook.setExtension(req.getAddress().getExtension());
        addressBook.setPostalCode(req.getAddress().getPostalCode());

        return addressBook;
    }

    public static void updateAddressBook(AddressBook addressBook, AddressBookUpdateRequestDTO req) {
        if ((req.getContact().getEnglishFirstName() != null) && (req.getContact().getEnglishLastName() != null))
            addressBook.setName(req.getContact().getEnglishFirstName() + " " + req.getContact().getEnglishLastName());
        addressBook.setEmailAddressMain(req.getContact().getEmailAddressMain() != null ? req.getContact().getEmailAddressMain() : addressBook.getEmailAddressMain());
        addressBook.setEmailAddressSecondary(req.getContact().getEmailAddressSecondary() != null ? req.getContact().getEmailAddressSecondary(): addressBook.getEmailAddressSecondary());
        addressBook.setPhoneNumber(req.getContact().getPhoneNumber() != null ? req.getContact().getPhoneNumber() : addressBook.getPhoneNumber());
        addressBook.setAbout(req.getContact().getAbout() != null ? req.getContact().getAbout() : addressBook.getAbout());

        addressBook.setAddressLine(req.getAddress().getAddressLine() != null ? req.getAddress().getAddressLine() : addressBook.getAddressLine());
        addressBook.setTelephoneNumber(req.getAddress().getTelephoneNumber() != null ? req.getAddress().getTelephoneNumber() : addressBook.getTelephoneNumber());
        addressBook.setExtension(req.getAddress().getExtension() != null ? req.getAddress().getExtension() : addressBook.getExtension());
        addressBook.setPostalCode(req.getAddress().getPostalCode() != null ? req.getAddress().getPostalCode() : addressBook.getPostalCode());
    }

}
