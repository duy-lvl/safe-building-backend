package com.safepass.safebuilding.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/***
 * This class is DTO for mobile app add service
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddServiceDTO {
    private String customerId;
    private String serviceid;
    private String contractId;
    private int quantity;
}
