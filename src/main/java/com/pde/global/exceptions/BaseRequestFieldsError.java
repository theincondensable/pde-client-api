package com.pde.global.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.List;

/**
 * @author abbas
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BaseRequestFieldsError {
    private List<RequestFieldsError> fieldsErrors;
    private final Integer errorCode = HttpStatus.NOT_ACCEPTABLE.value();
    private final String errorDesc = HttpStatus.NOT_ACCEPTABLE.getReasonPhrase();
}
