package com.safepass.safebuilding.service.dto;

import com.safepass.safebuilding.common.meta.ServiceStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestObjectForCreate {
    private String name;
    private String description;
    private String price;
    private String status;
}
