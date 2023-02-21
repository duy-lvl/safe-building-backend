package com.safepass.safebuilding.customer.controller;

import com.safepass.safebuilding.common.dto.ResponseObject;
import com.safepass.safebuilding.customer.entity.Customer;
import com.safepass.safebuilding.customer.service.CustomerService;
import com.safepass.safebuilding.device.entity.Device;
import com.safepass.safebuilding.device.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

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
        Optional<Customer> customer = customerService.getCustomerById(UUID.fromString(customerId));
        if (customer.isPresent()) {
            Device device = deviceService.addToken(customer.get(), token);
            if (device != null) {
                ResponseEntity<ResponseObject> responseEntity = ResponseEntity.status(HttpStatus.CREATED)
                        .body(new ResponseObject(HttpStatus.CREATED.toString(), "Successfully", null, null));
                return responseEntity;
            }
        }

        ResponseEntity<ResponseObject> responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ResponseObject(HttpStatus.BAD_REQUEST.toString(), "Device existed", null, null));
        return responseEntity;
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
}
