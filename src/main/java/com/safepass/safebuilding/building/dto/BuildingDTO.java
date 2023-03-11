package com.safepass.safebuilding.building.dto;

import com.safepass.safebuilding.common.meta.BuildingStatus;
import lombok.*;

import java.io.Serializable;

@Setter
@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BuildingDTO implements Serializable {
    private String id;
    private String name;
    private String address;
    private BuildingStatus status;
    private int capacity;
}
