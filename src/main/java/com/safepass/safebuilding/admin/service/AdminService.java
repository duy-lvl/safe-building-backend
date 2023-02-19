package com.safepass.safebuilding.admin.service;


import com.safepass.safebuilding.common.dto.ResponseObject;
import org.springframework.http.ResponseEntity;

public interface AdminService {
    ResponseEntity<ResponseObject> login(String phoneOrEmail, String password);

    ResponseEntity<ResponseObject> loginWithEmail(String email);
}
