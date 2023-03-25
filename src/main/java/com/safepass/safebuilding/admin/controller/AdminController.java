package com.safepass.safebuilding.admin.controller;


import com.safepass.safebuilding.admin.service.AdminService;
import com.safepass.safebuilding.common.dto.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/v1/admins")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public String testApi() {
        return "Hello world admin";
    }

    @GetMapping("/accounts")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseObject> getAccount(
            @RequestParam(name = "page", defaultValue = "1")  int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        return adminService.getAccountList(page, size);
    }

}
