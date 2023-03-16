package com.safepass.safebuilding.batch_job.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerInfoBatchJob {
    private UUID flatId;
    private UUID contractId;
    private UUID customerId;
    private String fullname;
    private String phoneToken;
    private String email;
    private UUID billId;
}
