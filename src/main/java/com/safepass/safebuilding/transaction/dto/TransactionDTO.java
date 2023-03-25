package com.safepass.safebuilding.transaction.dto;

import com.safepass.safebuilding.common.meta.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class TransactionDTO {
    private Date date;
    private int amount;
    private String description;
    private TransactionStatus status;
}
