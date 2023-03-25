package com.safepass.safebuilding.customer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestObjectForCreateCustomer {
    private String phone;
    private String email;
    private String fullName;
    private String password;
    private String address;
    private String dateOfBirth;
    private String citizenId;
    private String gender;

}
