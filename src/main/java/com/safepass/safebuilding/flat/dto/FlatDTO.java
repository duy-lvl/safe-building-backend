package com.safepass.safebuilding.flat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlatDTO implements Serializable {
    private String id;
    private String buildingName;
    private int roomNumber;
    private String flatType;
    private int price;
    private String status;
}
