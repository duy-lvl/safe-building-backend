package com.safepass.safebuilding.facility.controller;

import com.safepass.safebuilding.facility.service.FacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/furniture")
public class FacilityController {
    @Autowired
    private FacilityService furnitureService;
}
