package com.safepass.safebuilding.customer.controller;

import com.safepass.safebuilding.common.dto.ResponseObject;
import com.safepass.safebuilding.common.exception.InvalidPageSizeException;
import com.safepass.safebuilding.common.exception.MaxPageExceededException;
import com.safepass.safebuilding.customer.entity.Customer;
import com.safepass.safebuilding.customer.service.CustomerService;
import com.safepass.safebuilding.device.entity.Device;
import com.safepass.safebuilding.device.service.DeviceService;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value = "/api/v1/web/customers")
public class CustomerController {

    @Autowired
    static HttpServletRequest request;

    @Autowired
    static HttpServletResponse response;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private DeviceService deviceService;

    @PostMapping("/{customerId}/add-device")
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
    public ResponseEntity<ResponseObject> getList (
            @RequestParam(name = "page", defaultValue = "1")  int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) throws InvalidPageSizeException, MaxPageExceededException {
        return customerService.getCustomerList(page, size);
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

    @GetMapping("/devices")
    public ResponseEntity<ResponseObject> getCustomerDevice(
            @RequestParam(name = "page", defaultValue = "1")  int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {

        return customerService.getCustomerDeviceList(page, size);
    }
}
