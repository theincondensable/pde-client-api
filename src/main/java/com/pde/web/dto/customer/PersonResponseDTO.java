package com.pde.web.dto.customer;

import com.pde.business.model.auth.User;
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
public class PersonResponseDTO {
    private String englishFirstName;
    private String englishLastName;

    public PersonResponseDTO(User user) {
        this.englishFirstName = user.getEnglishFirstName();
        this.englishLastName = user.getEnglishLastName();
    }
}
