package com.safepass.safebuilding.building.dto;

import com.safepass.safebuilding.common.meta.BuildingStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuildingPostRequest {
    private String name;
    private String address;
    @Enumerated(EnumType.STRING)
    private BuildingStatus status;
    private int capacity;
}
