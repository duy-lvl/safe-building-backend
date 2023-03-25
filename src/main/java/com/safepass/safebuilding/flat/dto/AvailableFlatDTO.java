package com.safepass.safebuilding.flat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvailableFlatDTO {
    private String id;
    private int roomNumber;
    private int value;
}
