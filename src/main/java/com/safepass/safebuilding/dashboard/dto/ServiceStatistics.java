package com.safepass.safebuilding.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceStatistics {
    private String name;
    private int quantity;
    private int value;
}
