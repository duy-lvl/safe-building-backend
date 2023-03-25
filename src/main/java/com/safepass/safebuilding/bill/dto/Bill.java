package com.safepass.safebuilding.bill.dto;

import com.safepass.safebuilding.common.meta.BillStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bill {
    private UUID id;
    private Date date;
    private int value;
    private BillStatus status;
    private String buildingName;
    private int room_number;
    private String customerName;
}
