package com.pde.web.controllers;

import com.pde.business.model.auth.User;
import com.pde.business.model.customer.Company;
import com.pde.business.service.customer.CompanyService;
import com.pde.business.service.user.UserService;
import com.pde.global.BaseResponse;
import com.pde.web.dto.customer.CompanyResponseDTO;
import com.pde.web.dto.customer.PersonResponseDTO;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author abbas
 */
@RestController
@RequestMapping("/v1/dev")
public class DeveloperController {

    private final UserService userService;
    private final CompanyService companyService;

    public DeveloperController(UserService userService, CompanyService companyService) {
        this.userService = userService;
        this.companyService = companyService;
    }

    @Profile("dev")
    @GetMapping("/all/users")
    public BaseResponse<List<PersonResponseDTO>> getAllUsers() {
        return new BaseResponse<>(
                userService.getAllUsers().stream().map(
                        PersonResponseDTO::new
                ).toList()
        );
    }

    @Profile("dev")
    @GetMapping("/all/companies")
    public BaseResponse<List<CompanyResponseDTO>> getAllCompanies() {
        return new BaseResponse<>(
                companyService.findAll().stream().map(
                        CompanyResponseDTO::new
                ).toList()
        );
    }

}
