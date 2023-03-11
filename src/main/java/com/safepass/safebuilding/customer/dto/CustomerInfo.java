package com.safepass.safebuilding.customer.dto;

import com.safepass.safebuilding.common.meta.CustomerStatus;
import com.safepass.safebuilding.device.dto.DeviceDTO;
import com.safepass.safebuilding.device.entity.Device;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerInfo {
    private String id;
    private String phone;
    private String email;
    private String fullname;
    private String address;
    private String dateOfBirth;
    private String gender;
    private String citizenId;
    private CustomerStatus status;
    private List<ContractDTO> contract; //contract and room_number
    private String dateJoin;
    private List<DeviceDTO> devices;
}
