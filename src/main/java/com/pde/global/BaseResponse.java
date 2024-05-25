package com.pde.global;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * @author abbas
 */
@Getter
@Setter(value = AccessLevel.PUBLIC)
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse<T> {

    private Integer code = HttpStatus.OK.value();
    private String codeDesc = HttpStatus.OK.getReasonPhrase();
    private T data;

    public BaseResponse(T data) {
        this.code = HttpStatus.OK.value();
        this.codeDesc = HttpStatus.OK.getReasonPhrase();
        this.data = ResponseEntity.ok(data).getBody();
    }

    @Override
    public String toString() {
        return "{" +
                "code=" + code +
                ", codeDesc='" + codeDesc + '\'' +
                ", data=" + data +
                '}';
    }
}
