package com.safepass.safebuilding.customer.service;

import com.safepass.safebuilding.common.dto.ResponseObject;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.safepass.safebuilding.customer.entity.Customer;

import java.util.Optional;
import java.util.UUID;

public interface CustomerService {
    ResponseEntity<ResponseObject> login(String phoneOrEmail, String password);
    ResponseEntity<ResponseObject> loginWithEmail(String email);
    ResponseEntity<ResponseObject> getCustomerList(int page, int size);
    Optional<Customer> getCustomerById(UUID id);
    ResponseEntity<ResponseObject> getAccountList(int page, int size);
}
