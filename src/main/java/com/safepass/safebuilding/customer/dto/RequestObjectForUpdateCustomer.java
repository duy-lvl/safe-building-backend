package com.safepass.safebuilding.customer.dto;

import com.safepass.safebuilding.common.meta.CustomerStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequestObjectForUpdateCustomer {
    private String id;
    private String phone;
    private String email;
    private String fullname;
    private String address;
    private String dateOfBirth;
    private String gender;
    private String citizenId;
    private String status;
}
