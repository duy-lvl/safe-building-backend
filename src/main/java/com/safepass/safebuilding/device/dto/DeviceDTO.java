package com.safepass.safebuilding.device.dto;

import com.safepass.safebuilding.customer.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceDTO {
    private String token;
    private Customer customer;
}
