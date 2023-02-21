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
    private BuildingStatus status;
}
