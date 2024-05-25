package com.pde.business.service.information.service;

import com.pde.business.exceptions.information.AddressBookNotFoundException;
import com.pde.business.exceptions.information.AddressBookOwnerException;
import com.pde.business.model.auth.User;
import com.pde.business.model.information.AddressBook;
import com.pde.business.repository.information.AddressBookRepository;
import com.pde.business.service.LoggedInUserExtractor;
import com.pde.web.dto.information.addresses.AddressBookCreateRequestDTO;
import com.pde.web.dto.information.addresses.AddressBookSimpleResponseDTO;
import com.pde.web.dto.information.addresses.AddressBookUpdateRequestDTO;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * @author abbas
 */
@Service
@AllArgsConstructor
public class AddressBookService {

    private final String className = this.getClass().getSimpleName();

    private final LoggedInUserExtractor userExtractor;
    private final AddressBookRepository addressBookRepository;

    public AddressBook findAddressBookById(Long id) {
        return addressBookRepository.findById(id).orElseThrow(
                () -> new AddressBookNotFoundException(className)
        );
    }

//    public AddressBook findAddressBookByUser(User user) {
//        return addressBookRepository.findByUser(user);
//    }

    public List<AddressBook> findAll() {
        return addressBookRepository.findAll();
    }

    public List<AddressBookSimpleResponseDTO> findAllPaginated(PageRequest page) {
        User loggedInUser = userExtractor.getLoggedInUser();

        return addressBookRepository.findAllByUsersOrderByCreatedAtDesc(Set.of(loggedInUser), page)
                .stream().map(AddressBookSimpleResponseDTO::fromAddressBook).toList();
    }

    public void createAddressBook(AddressBookCreateRequestDTO req) {
        User loggedInUser = userExtractor.getLoggedInUser();

        AddressBook addressBook = AddressBook.createAddressBook(loggedInUser, req);

        addressBookRepository.save(addressBook);
    }

    public void updateAddressBook(AddressBookUpdateRequestDTO req) {
        User loggedInUser = userExtractor.getLoggedInUser();
        AddressBook addressBook = findAddressBookById(req.getAddressBookId());

        if (!addressBook.getUsers().contains(loggedInUser))
            throw new AddressBookOwnerException(className);

        AddressBook.updateAddressBook(addressBook, req);

        addressBookRepository.save(addressBook);
    }

    public void deleteAddressBook(Long addressBookId) {
        User loggedInUser = userExtractor.getLoggedInUser();

        AddressBook addressBook = findAddressBookById(addressBookId);

        if (!addressBook.getUsers().contains(loggedInUser))
            throw new AddressBookOwnerException(className);

        addressBookRepository.deleteById(addressBookId);
    }

}
