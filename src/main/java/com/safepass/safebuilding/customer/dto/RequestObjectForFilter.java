package com.safepass.safebuilding.customer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequestObjectForFilter {
    private String searchString;
    private String buildingId;
}
