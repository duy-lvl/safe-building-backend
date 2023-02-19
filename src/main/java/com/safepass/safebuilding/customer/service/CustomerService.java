package com.safepass.safebuilding.customer.service;

import com.safepass.safebuilding.common.dto.ResponseObject;
import org.springframework.http.ResponseEntity;

public interface CustomerService {
    ResponseEntity<ResponseObject> login(String phoneOrEmail, String password);

    ResponseEntity<ResponseObject> loginWithEmail(String email);
}
