package com.safepass.safebuilding.flat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequestFlat {
    private String detail;
    private int price;
    private String status;
    private int roomNumber;
    private String flatTypeId;
    private String buildingId;
}
