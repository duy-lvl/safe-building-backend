package com.safepass.safebuilding.device.service.impl;

import com.safepass.safebuilding.customer.entity.Customer;
import com.safepass.safebuilding.device.entity.Device;
import com.safepass.safebuilding.device.repository.DeviceRepository;
import com.safepass.safebuilding.device.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeviceServiceImpl implements DeviceService {
    @Autowired
    private DeviceRepository deviceRepository;

    @Override
    public Device addToken(Customer customer, String token) {
        Device device = new Device(UUID.randomUUID(), token, customer);
        return deviceRepository.save(device);
    }

}
