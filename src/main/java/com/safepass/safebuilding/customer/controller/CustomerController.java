package com.safepass.safebuilding.customer.controller;

import com.safepass.safebuilding.common.dto.ResponseObject;
import com.safepass.safebuilding.common.exception.InvalidPageSizeException;
import com.safepass.safebuilding.common.exception.MaxPageExceededException;
import com.safepass.safebuilding.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:8082")
@RequestMapping(value = "/api/v1/customer")
public class CustomerController {
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
}
