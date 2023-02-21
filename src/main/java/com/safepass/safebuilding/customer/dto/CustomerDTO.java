package com.safepass.safebuilding.customer.dto;

import com.safepass.safebuilding.common.meta.CustomerStatus;
import com.safepass.safebuilding.device.dto.DeviceDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {
    private String customerId;
    private String citizenId;
    private String fullname;
    private String phone;
    private String buildingName;
    private int roomNumber;
    private CustomerStatus status;
    private List<DeviceDTO> device;
}
