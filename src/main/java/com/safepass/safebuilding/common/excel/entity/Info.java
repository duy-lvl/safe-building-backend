package com.safepass.safebuilding.common.excel.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Info {
    private UUID contractId;
    private UUID customerId;
    private UUID flatId;
    private int roomNumber;
}
