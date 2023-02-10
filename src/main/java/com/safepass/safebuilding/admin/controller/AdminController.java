package com.safepass.safebuilding.admin.controller;


import com.safepass.safebuilding.admin.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @GetMapping
    public String testApi(){
        return "Hello world";
    }


}
