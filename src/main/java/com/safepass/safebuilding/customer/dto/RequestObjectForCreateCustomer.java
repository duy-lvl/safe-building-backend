package com.safepass.safebuilding.customer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestObjectForCreateCustomer {
    @NotNull
    private String phone;

    @NotNull
    private String fullName;

    @NotNull
    private String password;

    @NotNull
    private String confirmPassword;

    private String dateOfBirth;
    private String gender;

    @NotNull
    private String buildingId;

    @NotNull
    private String flatId;

    @NotNull
    private String expiryDay;

    @NotNull
    private int value;

    @NotNull
    private MultipartFile[] files;
}
