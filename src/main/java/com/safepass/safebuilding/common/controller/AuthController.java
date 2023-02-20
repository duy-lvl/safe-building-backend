package com.safepass.safebuilding.common.controller;

import com.safepass.safebuilding.admin.service.AdminService;
import com.safepass.safebuilding.common.dto.LoginForm;
import com.safepass.safebuilding.common.dto.ResponseObject;
import com.safepass.safebuilding.customer.service.CustomerService;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private AdminService adminService;

    @PostMapping("/web/login")
    @SecurityRequirements
    @PreAuthorize("permitAll()")
    public ResponseEntity<ResponseObject> loginWeb(@RequestBody LoginForm loginForm) {
        return adminService.login(loginForm.getPhone(), loginForm.getPassword());
    }

    @PostMapping("/web/login-with-email")
    @SecurityRequirements
    @PreAuthorize("permitAll()")
    public ResponseEntity<ResponseObject> loginWebWithEmail(@RequestBody LoginForm login) {
        return adminService.loginWithEmail(login.getEmail());
    }

    @PostMapping("/mobile/login")
    @SecurityRequirements
    @PreAuthorize("permitAll()")
    public ResponseEntity<ResponseObject> loginMobile(@RequestBody LoginForm loginForm) {
        return customerService.login(loginForm.getPhone(), loginForm.getPassword());
    }

    @PostMapping("/mobile/login-with-email")
    @SecurityRequirements
    @PreAuthorize("permitAll()")
    public ResponseEntity<ResponseObject> loginMobileWithEmail(@RequestBody LoginForm loginForm) {
        return customerService.loginWithEmail(loginForm.getEmail());
    }
}
