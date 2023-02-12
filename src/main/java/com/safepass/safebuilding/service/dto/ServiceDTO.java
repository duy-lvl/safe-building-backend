package com.safepass.safebuilding.service.dto;

import com.safepass.safebuilding.common.meta.ServiceStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceDTO {

    private String name;
    private String description;
    private int price;
    private ServiceStatus status;
}
