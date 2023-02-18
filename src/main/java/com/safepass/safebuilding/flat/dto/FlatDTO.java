package com.safepass.safebuilding.flat.dto;

import com.safepass.safebuilding.common.meta.FlatStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlatDTO {
    private String buildingName;
    private int roomNumber;
    private String flatType;
    private int price;
    private String status;
}
