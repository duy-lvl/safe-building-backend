package com.safepass.safebuilding.rent_contract.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequestObjectForCreate {
    private String customerId;
    private String flatId;
    private String startDate;
    private String expiryDate;
    private String title;
    private int value;
}
