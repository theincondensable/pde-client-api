package com.pde.web.dto.customer;

import com.pde.business.model.customer.Company;
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
public class CompanyResponseDTO {
    private PersonResponseDTO contactPerson;
    private String companyName;

    public CompanyResponseDTO(Company company) {
        if (company.getCompanyContactPerson() != null)
            this.contactPerson = new PersonResponseDTO(company.getCompanyContactPerson());
        this.companyName = company.getEnglishCompanyName();
    }
}
