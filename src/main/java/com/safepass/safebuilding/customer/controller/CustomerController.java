package com.safepass.safebuilding.customer.controller;

import com.safepass.safebuilding.common.dto.ResponseObject;
import com.safepass.safebuilding.common.exception.InvalidDataException;
import com.safepass.safebuilding.common.exception.InvalidPageSizeException;
import com.safepass.safebuilding.common.exception.MaxPageExceededException;
import com.safepass.safebuilding.common.exception.NoSuchDataException;
import com.safepass.safebuilding.customer.dto.RequestObjectForCreateCustomer;
import com.safepass.safebuilding.customer.dto.RequestObjectForFilter;
import com.safepass.safebuilding.customer.dto.RequestObjectForUpdateCustomer;
import com.safepass.safebuilding.customer.service.CustomerService;
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


    @PostMapping("/{customerId}/add-device")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseObject> addDevice (
            @RequestParam(name = "token") String token,
            @PathVariable(name = "customerId") String customerId
    ) {
        return customerService.addDevice(customerId, token);
    }


//    @GetMapping
//    @PreAuthorize("hasAuthority('ADMIN')")
//    public ResponseEntity<ResponseObject> getList (
//            @RequestParam(name = "page", defaultValue = "1")  int page,
//            @RequestParam(name = "size", defaultValue = "10") int size
//    ) throws MaxPageExceededException, InvalidPageSizeException, NoSuchDataException {
//        return customerService.getCustomerList(page, size);
//    }

//    @GetMapping("/test")
//    @PreAuthorize("hasAuthority('CUSTOMER')")
//    public String testApi() {
//        return "Hello world customer";
//    }


//    @GetMapping("/accounts")
//    @PreAuthorize("hasAuthority('ADMIN')")
//    public ResponseEntity<ResponseObject> getAccountList(
//            @RequestParam(name = "page", defaultValue = "1")  int page,
//            @RequestParam(name = "size", defaultValue = "10") int size
//    ) throws InvalidPageSizeException, MaxPageExceededException, NoSuchDataException {
//        return customerService.getAccountList(page, size);
//    }

    @GetMapping("/filter")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseObject> filterCustomer(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String searchKey,
            @RequestParam(defaultValue = "") String sortBy,
            @RequestParam(defaultValue = "") String order
    ) throws InvalidPageSizeException, MaxPageExceededException, NoSuchDataException {
        RequestObjectForFilter requestObjectForFilter = new RequestObjectForFilter(page, size, searchKey, sortBy, order);
        return customerService.filterCustomer(requestObjectForFilter);
    }

    @PostMapping("/create-customer")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseObject> addCustomer(@RequestBody RequestObjectForCreateCustomer requestObject)
            throws SQLException, InvalidDataException {
        return customerService.addCustomer(requestObject);
    }

    @PutMapping("/update-customer")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseObject> updateCustomer(@RequestBody RequestObjectForUpdateCustomer requestObject)
            throws InvalidDataException {
        return customerService.updateCustomer(requestObject);
    }

    @GetMapping("/{customerId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CUSTOMER')")

    public ResponseEntity<ResponseObject> getCustomer(@PathVariable String customerId)
            throws NoSuchDataException {
        return customerService.getCustomer(customerId);
    }

    @GetMapping("/{customerId}/contracts")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<ResponseObject> getCustomerContracts(@PathVariable String customerId)
            throws NoSuchDataException {
        return customerService.getCustomerContract(customerId);
    }
}
