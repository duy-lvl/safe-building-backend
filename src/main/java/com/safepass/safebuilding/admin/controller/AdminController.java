package com.safepass.safebuilding.admin.controller;


import com.safepass.safebuilding.admin.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/v1/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public String testApi() {
        return "Hello world admin";
    }


}
