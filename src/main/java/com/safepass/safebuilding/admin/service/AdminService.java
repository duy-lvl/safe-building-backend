package com.safepass.safebuilding.admin.service;


import com.safepass.safebuilding.common.dto.ResponseObject;
import org.springframework.http.ResponseEntity;

import java.util.concurrent.ExecutionException;

public interface AdminService {
    ResponseEntity<ResponseObject> login(String phone, String password);

    ResponseEntity<ResponseObject> loginWithEmail(String email, String token) throws ExecutionException, InterruptedException;
}
