package com.safepass.safebuilding.building.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuildingGetRequest {
    private int page = 1;
    private int size = 10;
    private String searchKey = "";
    private String sortBy;
    private String order;
}
