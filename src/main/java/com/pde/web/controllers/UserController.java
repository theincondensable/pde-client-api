package com.pde.web.controllers;

import com.pde.business.service.user.UserService;
import com.pde.global.BaseResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author abbas
 */
@RestController
@RequestMapping("/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/test")
    @PreAuthorize("hasAuthority('ADMIN_DEVELOPER')")
//    @PreAuthorize("hasAuthority('FORMAL_USER')")
    public BaseResponse<String> testLogin() {
        return new BaseResponse<>("HEEEEY");
    }

}

/**
 * localhost:8090/api/swagger-ui/index.html
 */