package com.pde.web.controllers;

import com.pde.business.model.information.AddressBook;
import com.pde.business.service.information.service.AddressBookService;
import com.pde.global.BaseResponse;
import com.pde.web.dto.information.addresses.AddressBookCreateRequestDTO;
import com.pde.web.dto.information.addresses.AddressBookSimpleResponseDTO;
import com.pde.web.dto.information.addresses.AddressBookUpdateRequestDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author abbas
 */
@RestController
@RequestMapping("/v1/info")
public class InformationController {

    private final AddressBookService addressBookService;

    public InformationController(AddressBookService addressBookService) {
        this.addressBookService = addressBookService;
    }

    @GetMapping("/addressBooks")
    public BaseResponse<List<AddressBookSimpleResponseDTO>> getAddressBooks(@RequestParam Integer page, @RequestParam Integer size) {
        return new BaseResponse<>(addressBookService.findAllPaginated(PageRequest.of(page, size)));
    }

    @PostMapping("/addressBook")
    public BaseResponse<String> createAddressBook(@Valid @RequestBody AddressBookCreateRequestDTO req) {
        addressBookService.createAddressBook(req);
        return new BaseResponse<>("Address Book Created for User.");
    }

    @PutMapping("/addressBook")
    public BaseResponse<String> updateAddressBook(@Valid @RequestBody AddressBookUpdateRequestDTO req) {
        addressBookService.updateAddressBook(req);
        return new BaseResponse<>("Address Book Created for User.");
    }

    @DeleteMapping("/addressBook/{addressBookId}")
    public BaseResponse<String> deleteAddressBook(@PathVariable Long addressBookId) {
        addressBookService.deleteAddressBook(addressBookId);
        return new BaseResponse<>("Address Book Deleted for User.");
    }

}
