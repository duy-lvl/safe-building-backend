package com.safepass.safebuilding.bill.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillCreate {
    private String customerId;
    private ServiceDTO[] service;
    private String contractId;

}
