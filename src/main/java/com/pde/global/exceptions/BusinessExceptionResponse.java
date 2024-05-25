package com.pde.global.exceptions;

import com.pde.global.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author abbas
 */
@Getter
@NoArgsConstructor
public class BusinessExceptionResponse extends BaseResponse {

    private String exceptionMessage;
    private String status;
    private Integer statusCode;
    public BusinessExceptionResponse(String exceptionMessage, String status, Integer statusCode) {
        super(null, null, null);
        this.exceptionMessage = exceptionMessage;
        this.status = status;
        this.statusCode = statusCode;
    }
}
