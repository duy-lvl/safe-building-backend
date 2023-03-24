package com.safepass.safebuilding.customer.dto;

import com.safepass.safebuilding.common.meta.RentContractStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContractDTO implements Serializable{
    private String id;
    private String link;
    private int roomNumber;
    private RentContractStatus status;
    private String title;
    private String buildingName;
    private String buildAddress;
}
