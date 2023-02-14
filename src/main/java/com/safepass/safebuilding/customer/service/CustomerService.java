package com.safepass.safebuilding.customer.service;

import com.safepass.safebuilding.common.dto.ResponseObject;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface CustomerService {
    ResponseEntity<ResponseObject> login(HttpServletResponse response, HttpServletRequest request, String phoneOrEmail, String password);

    ResponseEntity<ResponseObject> loginWithEmail(HttpServletResponse response, HttpServletRequest request, String email);
}
