package com.safepass.safebuilding.service.controller;

import com.safepass.safebuilding.service.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/service")
public class ServiceController {
    @Autowired
    private ServiceService serviceService;
}
