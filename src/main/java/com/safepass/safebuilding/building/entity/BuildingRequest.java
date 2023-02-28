package com.safepass.safebuilding.building.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuildingRequest {
    private int page = 1;
    private int size = 10;
    private String searchKey = "";
    private String sortName;
    private String sortAddress;
    private String sortStatus;
}
