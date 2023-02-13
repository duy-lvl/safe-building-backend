package com.safepass.safebuilding.customer.service;

import com.safepass.safebuilding.common.dto.ResponseObject;
import org.springframework.http.ResponseEntity;

public interface CustomerService {
    ResponseEntity<ResponseObject> getAllCustomer(int page, int size);
}
