package com.safepass.safebuilding.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonthStatistics {
    private int total;
    private int percent;
    private String status;
}
