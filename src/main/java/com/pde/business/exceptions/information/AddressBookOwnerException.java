package com.pde.business.exceptions.information;

import com.pde.global.exceptions.BusinessException;
import org.springframework.http.HttpStatus;

/**
 * @author abbas
 */
public class AddressBookOwnerException extends BusinessException {
    public AddressBookOwnerException(String className) {
        super(new BusinessException(
                "address.book.owner.can.change.it",
                null,
                HttpStatus.BAD_REQUEST,
                className
        ));
    }
}
