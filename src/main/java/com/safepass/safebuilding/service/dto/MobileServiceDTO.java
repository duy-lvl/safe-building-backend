package com.safepass.safebuilding.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MobileServiceDTO {
    private String id;
    private String name;
    private String description;
    private int price;
    private String icon;
}
