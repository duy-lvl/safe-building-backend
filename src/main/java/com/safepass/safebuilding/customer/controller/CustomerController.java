package com.safepass.safebuilding.customer.controller;

import com.safepass.safebuilding.admin.service.AdminService;
import com.safepass.safebuilding.common.dto.ResponseObject;
import com.safepass.safebuilding.customer.service.CustomerService;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/api/v1/customer")
public class CustomerController {

    @Autowired
    static HttpServletRequest request;

    @Autowired
    static HttpServletResponse response;

    @Autowired
    private CustomerService customerService;

    @GetMapping
    @PostAuthorize("hasRole('ROLE_CUSTOMER')")
    public String testApi(){
        return "Hello world customer";
    }

    @PostMapping("/mobile/login")
    @SecurityRequirements
//    @PreAuthorize("permitAll()")
    public ResponseEntity<ResponseObject> login(@Param("phone") String phone, @Param("password") String password) {
        return customerService.login(response, request, phone, password);
    }

    @PostMapping("/mobile/login-with-email")
    @SecurityRequirements
//    @PreAuthorize("permitAll()")
    public ResponseEntity<ResponseObject> loginWithEmail(@Param("email") String email) {
        return customerService.loginWithEmail(response, request, email);
    }
}
