package com.pde.business.service.customer;

import com.pde.business.exceptions.customer.CompanyDuplicateNationalRegistrationCodeException;
import com.pde.business.model.auth.User;
import com.pde.business.model.customer.Company;
import com.pde.business.repository.customer.CompanyRepository;
import com.pde.web.dto.auth.register.CompanyUserRegisterRequestDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author abbas
 */
@Service
@AllArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;

    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    public Company findById(Long id) {
        return companyRepository.findById(id).get();
    }

    public Company registerCompany(User companyContactPerson, CompanyUserRegisterRequestDTO req) {
        validateNationalRegistrationCode(req.getNationalRegistrationCode());

        Company company = Company.createCompany(companyContactPerson, req);
        return companyRepository.save(company);
    }

    public void validateNationalRegistrationCode(String nationalRegistrationCode) {
        if (companyRepository.existsCompanyByNationalRegistrationCode(nationalRegistrationCode))
            throw new CompanyDuplicateNationalRegistrationCodeException(this.getClass().getSimpleName(), nationalRegistrationCode);
    }

}
