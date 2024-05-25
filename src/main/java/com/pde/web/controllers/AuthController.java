package com.pde.web.controllers;

import com.pde.business.model.auth.User;
import com.pde.business.model.customer.Company;
import com.pde.business.service.auth.AuthService;
import com.pde.business.service.auth.OtpService;
import com.pde.business.service.customer.CompanyService;
import com.pde.business.service.user.UserService;
import com.pde.config.log.Log;
import com.pde.global.BaseResponse;
import com.pde.web.dto.auth.ChangePasswordRequestDTO;
import com.pde.web.dto.auth.LoginWithEmailRequestDTO;
import com.pde.web.dto.auth.login.OtpValidationRequestDTO;
import com.pde.web.dto.auth.register.CompanyUserRegisterRequestDTO;
import com.pde.web.dto.auth.register.PersonUserRegisterRequestDTO;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author abbas
 */
@RestController
@RequestMapping("/v1/auth")
public class AuthController {

    private final CompanyService companyService;
    private final AuthService authService;
    private final UserService userService;
    private final OtpService otpService;

    public AuthController(CompanyService companyService, AuthService authService, UserService userService, UserService userService1, OtpService otpService) {
        this.companyService = companyService;
        this.authService = authService;
        this.userService = userService1;
        this.otpService = otpService;
    }

    @PostMapping("/login")
    public BaseResponse<String> login(@RequestBody LoginWithEmailRequestDTO req) {
        return new BaseResponse<>(authService.login(req));
    }

    @PostMapping("/login/otp-code")
    public BaseResponse<Integer> sendOtpCode(String phoneNumber) {
        return new BaseResponse<>(otpService.sendOtpCodeToPhoneNumber(phoneNumber));
    }

    @PostMapping("/login/validate/otp-code")
    public BaseResponse<Boolean> validateOtpCode(@Valid @RequestBody OtpValidationRequestDTO req) {
        return new BaseResponse<>(otpService.validateEnteredOtpCode(req));
    }

    /**
     * <p>calling this API means that User wants to register Person Account</p>
     */
    @Log
    @PostMapping("/register/person")
    public BaseResponse<String> registerPerson(@Valid @RequestBody PersonUserRegisterRequestDTO req) {
        User person = userService.registerPersonAccount(req);
        return new BaseResponse<>("Peron User Account registered successfully with ID: " + person.getId());
    }

    /**
     * <p>calling this API means that User wants to register Company Account</p>
     */
    @PostMapping("/register/company")
    public BaseResponse<String> registerCompany(@Valid @RequestBody CompanyUserRegisterRequestDTO req) {
        User companyContactPerson = userService.registerCompanyAccount(req);
        Company company = companyService.registerCompany(companyContactPerson, req);
        return new BaseResponse<>("Company User Account registered successfully with ID: " + company.getId());
    }

    @PostMapping("/change-password")
    public BaseResponse<String> changePassword(@Valid @RequestBody ChangePasswordRequestDTO req) {
        userService.changePassword(req);
        return new BaseResponse<>("The Change Password is done successfully.");
    }

    @PostMapping("/logout")
    public String logout() {

        System.out.println("Logged out");
        return "";
    }

}
