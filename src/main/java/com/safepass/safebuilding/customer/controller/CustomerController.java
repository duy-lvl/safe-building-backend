package com.safepass.safebuilding.customer.controller;

import com.safepass.safebuilding.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping
    @PostAuthorize("hasRole('ROLE_CUSTOMER')")
    public String testApi(){
        return "Hello world customer";
    }
}
