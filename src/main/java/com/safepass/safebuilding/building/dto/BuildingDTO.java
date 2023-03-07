package com.safepass.safebuilding.building.dto;

import com.safepass.safebuilding.common.meta.BuildingStatus;
import lombok.*;

@Setter
@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BuildingDTO {
    private String id;
    private String name;
    private String address;
    private BuildingStatus status;
    private int capacity;
}
