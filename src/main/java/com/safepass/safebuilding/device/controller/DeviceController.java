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
@RequestMapping("/api/v1/web/devices")
public class DeviceController {
    @Autowired
    private DeviceService deviceService;

}
