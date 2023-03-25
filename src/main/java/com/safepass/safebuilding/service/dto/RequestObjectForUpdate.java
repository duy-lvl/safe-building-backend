package com.safepass.safebuilding.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestObjectForUpdate {
    private String id;
    private String name;
    private String description;
    private String price;
    private String status;
}
