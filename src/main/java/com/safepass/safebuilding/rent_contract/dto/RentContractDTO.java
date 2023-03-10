package com.safepass.safebuilding.rent_contract.dto;

import com.safepass.safebuilding.common.meta.RentContractStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentContractDTO {
    private String id;
    private String buildingName;
    private String buildingAddress;
    private int roomNumber;
    private String customerName;
    private String startDate;
    private String expiryDate;
    private RentContractStatus status;
    private String title;
    private String rentContractLink;
    private String customerId;
    private String flatId;
    private String buildingId;
}
