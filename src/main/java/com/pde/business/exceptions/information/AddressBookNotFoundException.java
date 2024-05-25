package com.pde.business.exceptions.information;

import com.pde.global.exceptions.BusinessException;
import org.springframework.http.HttpStatus;

/**
 * @author abbas
 */
public class AddressBookNotFoundException extends BusinessException {
    public AddressBookNotFoundException(String className) {
        super(new BusinessException(
                "address.book.not.found",
                null,
                HttpStatus.BAD_REQUEST,
                className
        ));
    }
}
