package com.safepass.safebuilding.admin.controller;


import com.safepass.safebuilding.admin.service.AdminService;
import com.safepass.safebuilding.common.dto.ResponseObject;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RestController
@RequestMapping(value = "/api/v1/admin")
public class AdminController {
    @Autowired
    static HttpServletRequest request;
    @Autowired
    static HttpServletResponse response;
    @Autowired
    private AdminService adminService;

    @GetMapping
//    @PostAuthorize("hasRole('ROLE_ADMIN')")
    public String testApi() {
        return "Hello world admin";
    }


    @PostMapping("/web/login")
    @SecurityRequirements
    @PreAuthorize("permitAll()")
    public ResponseEntity<ResponseObject> login(@Param("phone") String phone, @Param("password") String password) {
        return adminService.login(response, request, phone, password);
    }


}
