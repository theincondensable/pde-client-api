package com.pde.business.model.customer;

import com.pde.business.model.BaseEntity;
import com.pde.business.model.auth.User;
import com.pde.web.dto.auth.register.CompanyUserRegisterRequestDTO;
import jakarta.persistence.*;
import lombok.*;

/**
 * @author abbas
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Company extends BaseEntity {

    @Column(nullable = false)
    private String englishCompanyName;
    @Column(nullable = false)
    private String farsiCompanyName;

    @OneToOne(cascade = CascadeType.ALL, optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_USER", nullable = false)
    private User companyContactPerson;

    private String nationalRegistrationCode;

    public static Company createCompany(User companyContactPerson, CompanyUserRegisterRequestDTO req) {
        Company company = new Company();
        company.setFarsiCompanyName(req.getFarsiCompanyName());
        company.setEnglishCompanyName(req.getEnglishCompanyName());
        company.setCompanyContactPerson(companyContactPerson);
        company.setNationalRegistrationCode(req.getNationalRegistrationCode());

        return company;
    }

}
