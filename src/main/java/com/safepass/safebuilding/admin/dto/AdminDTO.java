package com.safepass.safebuilding.admin.dto;

import com.safepass.safebuilding.common.meta.AdminStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminDTO {
    private UUID id;
    private String password;
    private String fullname;
    private String phone;
    private String email;
    private AdminStatus status;
}
