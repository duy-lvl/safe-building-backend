package com.safepass.safebuilding.device.service;

import com.safepass.safebuilding.common.dto.ResponseObject;
import com.safepass.safebuilding.customer.entity.Customer;
import com.safepass.safebuilding.device.entity.Device;
import org.springframework.http.ResponseEntity;

import java.util.Optional;
import java.util.UUID;

public interface DeviceService {
    Device addToken(Customer customer, String token);

}
