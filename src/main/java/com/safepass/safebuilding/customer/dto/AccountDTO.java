package com.safepass.safebuilding.customer.dto;

import com.safepass.safebuilding.common.meta.CustomerStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {
    private UUID id;
    private String fullname;
    private String phone;
    private String email;
    private CustomerStatus status;
}
