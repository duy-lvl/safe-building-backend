package com.safepass.safebuilding.common.controller.bill.dto;

import com.safepass.safebuilding.common.meta.BillStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillDTO {
    private UUID id;
    private Date date;
    private int value;
    private BillStatus status;
}
