package com.safepass.safebuilding.device.controller;

import com.safepass.safebuilding.common.dto.ResponseObject;
import com.safepass.safebuilding.customer.entity.Customer;
import com.safepass.safebuilding.customer.service.CustomerService;
import com.safepass.safebuilding.device.entity.Device;
import com.safepass.safebuilding.device.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/device")
public class DeviceController {
    @Autowired
    private DeviceService deviceService;

    @Autowired
    private CustomerService customerService;

    @PostMapping("/add-device")
    public ResponseEntity<ResponseObject> addDevice (
            @RequestParam(name = "token") String token,
            @RequestParam(name = "customerId") String customerId
    ) {
//        System.out.println("/add-device");
        Optional<Customer> customer = customerService.getCustomerById(UUID.fromString(customerId));
        if (customer.isPresent()) {
            Device device = deviceService.addToken(customer.get(), token);
            if (device != null) {
                ResponseEntity<ResponseObject> responseEntity = ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseObject(HttpStatus.OK.toString(), "Successfully", null, null));
                return responseEntity;
            }
        }

//        System.out.println("/add-token");

        ResponseEntity<ResponseObject> responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ResponseObject(HttpStatus.BAD_REQUEST.toString(), "Device existed", null, null));
        return responseEntity;
    }

    @GetMapping()
    public ResponseEntity<ResponseObject> getAll(
            @RequestParam(name = "page", defaultValue = "1")  int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        return deviceService.getAllDevice(page, size);
    }
}
