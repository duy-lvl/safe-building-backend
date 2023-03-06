package com.safepass.safebuilding.customer.dto;

import com.safepass.safebuilding.common.meta.RentContractStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContractDTO {
    private String link;
    private int roomNumber;
    private RentContractStatus status;
    private String title;
    private String buildingName;
    private String buildAddress;
}
