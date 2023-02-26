package com.safepass.safebuilding.customer.controller;

import com.safepass.safebuilding.common.dto.ResponseObject;
import com.safepass.safebuilding.common.exception.InvalidDataException;
import com.safepass.safebuilding.customer.dto.RequestObjectForCreateCustomer;
import com.safepass.safebuilding.customer.dto.RequestObjectForFilter;
import com.safepass.safebuilding.customer.service.CustomerService;
import com.safepass.safebuilding.device.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;


@RestController
@RequestMapping(value = "/api/v1/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private DeviceService deviceService;

    @PostMapping("/{customerId}/add-device")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseObject> addDevice (
            @RequestParam(name = "token") String token,
            @PathVariable(name = "customerId") String customerId
    ) {
        return customerService.addDevice(customerId, token);
    }


    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseObject> getList (
            @RequestParam(name = "page", defaultValue = "1")  int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        return customerService.getCustomerList(page, size);
    }

    @GetMapping("/test")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public String testApi() {
        return "Hello world customer";
    }
    

    @GetMapping("/accounts")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseObject> getAccountList(
            @RequestParam(name = "page", defaultValue = "1")  int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        return customerService.getAccountList(page, size);
    }

    @PostMapping("/filter")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseObject> filterCustomer(
            @RequestParam(name = "page", defaultValue = "1")  int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestBody RequestObjectForFilter requestObjectForFilter
            ) {
        return customerService.filterCustomer(requestObjectForFilter, page, size);
    }

    @PostMapping("/create-customer")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseObject> addCustomer(@RequestBody RequestObjectForCreateCustomer requestObject) throws SQLException, InvalidDataException {
        return customerService.addCustomer(requestObject);
    }
}
