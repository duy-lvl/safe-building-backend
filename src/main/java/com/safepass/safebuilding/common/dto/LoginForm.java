package com.safepass.safebuilding.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginForm {
    private String phone;
    private String password;
    private String email;
    private String token;
    private boolean isWeb;
    private boolean withEmail;
}
