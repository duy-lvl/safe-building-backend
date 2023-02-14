package com.safepass.safebuilding.common.security.jwt.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse {
    private String token;
    private static String type = "Bearer";
    private String refreshToken;
    private UUID id;
    private String username;
    private String email;
    private List<String> roles;
}
