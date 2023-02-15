package com.safepass.safebuilding.customer.controller;

import com.safepass.safebuilding.common.dto.ResponseObject;
import com.safepass.safebuilding.common.exception.InvalidPageSizeException;
import com.safepass.safebuilding.common.exception.MaxPageExceededException;
import com.safepass.safebuilding.common.dto.ResponseObject;
import com.safepass.safebuilding.customer.service.CustomerService;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value = "/api/v1/customer")
public class CustomerController {

    @Autowired
    static HttpServletRequest request;

    @Autowired
    static HttpServletResponse response;

    @Autowired
    private CustomerService customerService;
    @GetMapping
    public ResponseEntity<ResponseObject> getAll(
            @RequestParam(name = "page", defaultValue = "1")  int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) throws InvalidPageSizeException, MaxPageExceededException {
        System.out.println("Get all");
        return customerService.getAllCustomer(page, size);
    }

    @GetMapping("/test")
    @PostAuthorize("hasRole('ROLE_CUSTOMER')")
    public String testApi() {
        return "Hello world customer";
    }

    @PostMapping("/mobile/login")
    @SecurityRequirements
//    @PreAuthorize("permitAll()")
    public ResponseEntity<ResponseObject> login(@RequestParam("phone") String phone, @RequestParam("password") String password) {
        return customerService.login(response, request, phone, password);
    }

    @PostMapping("/mobile/login-with-email")
    @SecurityRequirements
//    @PreAuthorize("permitAll()")
    public ResponseEntity<ResponseObject> loginWithEmail(@RequestParam("email") String email) {
        return customerService.loginWithEmail(response, request, email);
    }
}
