package com.safepass.safebuilding.common.security.jwt.payload.resquest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenRefreshRequest {
    @NotBlank
    private String refreshToken;
}
