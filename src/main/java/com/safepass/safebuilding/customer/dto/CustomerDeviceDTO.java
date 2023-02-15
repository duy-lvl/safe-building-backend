package com.safepass.safebuilding.customer.dto;

import com.safepass.safebuilding.device.dto.DeviceDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDeviceDTO {

    private UUID customerId;
    private String fullname;
    private List<DeviceDTO> device;
}
