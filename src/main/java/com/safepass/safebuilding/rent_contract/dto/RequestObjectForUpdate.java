package com.safepass.safebuilding.rent_contract.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequestObjectForUpdate {
    private String contractId;
    private String customerId;
    private String flatId;
    private String expiryDate;
    private String title;
    private int value;
    private boolean isChange;
    private String oldFlatId;
    private String oldLink;
}
