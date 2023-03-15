package com.safepass.safebuilding.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentContractStatitics {
    private String month;
    private int value;
}
