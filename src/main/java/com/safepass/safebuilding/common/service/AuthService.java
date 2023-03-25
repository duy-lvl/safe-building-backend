package com.safepass.safebuilding.common.service;

import com.safepass.safebuilding.admin.service.AdminService;
import com.safepass.safebuilding.common.dto.LoginForm;
import com.safepass.safebuilding.common.dto.ResponseObject;
import com.safepass.safebuilding.common.jwt.service.JwtService;
import com.safepass.safebuilding.customer.service.CustomerService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Log4j2
@Service
public class AuthService {
    @Autowired
    private AdminService adminService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;

    public ResponseEntity<ResponseObject> login(LoginForm loginForm) throws ExecutionException, InterruptedException {
        boolean isWeb = loginForm.isWeb();
        boolean withEmail = loginForm.isWithEmail();
        if (isWeb && withEmail) {
            return adminService.loginWithEmail(loginForm.getEmail(), loginForm.getToken());
        } else if (isWeb && !withEmail) {
            return adminService.login(loginForm.getPhone(), loginForm.getPassword());
        } else if (!isWeb && withEmail) {
            return customerService.loginWithEmail(loginForm.getEmail(), loginForm.getToken());
        } else if (!isWeb && !withEmail) {
            return customerService.login(loginForm.getPhone(), loginForm.getPassword());
        }
        return null;
    }
}
