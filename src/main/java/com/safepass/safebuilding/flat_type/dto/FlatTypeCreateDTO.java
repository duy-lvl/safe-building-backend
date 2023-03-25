package com.safepass.safebuilding.flat_type.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FlatTypeCreateDTO {
    private String name;
    private String description;
}
