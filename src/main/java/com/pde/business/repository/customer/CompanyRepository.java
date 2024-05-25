package com.pde.business.repository.customer;

import com.pde.business.model.customer.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author abbas
 */
@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    Boolean existsCompanyByNationalRegistrationCode(String nationalRegistrationCode);

}
