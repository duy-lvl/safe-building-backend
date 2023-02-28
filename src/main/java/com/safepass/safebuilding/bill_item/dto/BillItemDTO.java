package com.safepass.safebuilding.bill_item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillItemDTO {
    private UUID id;
    private String service;
    private String icon;
    private int quantity;
    private int value;
}
