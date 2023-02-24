package com.safepass.safebuilding.common.controller;

import com.safepass.safebuilding.admin.service.AdminService;
import com.safepass.safebuilding.common.dto.LoginForm;
import com.safepass.safebuilding.common.dto.ResponseObject;
import com.safepass.safebuilding.common.service.AuthService;
import com.safepass.safebuilding.customer.service.CustomerService;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private AdminService adminService;

    @Autowired
    AuthService authService;

    @PostMapping("/login")
    @SecurityRequirements
    @PreAuthorize("permitAll()")
    public ResponseEntity<ResponseObject> login(@RequestBody LoginForm loginForm) throws ExecutionException, InterruptedException {
        return authService.login(loginForm);
    }
}
