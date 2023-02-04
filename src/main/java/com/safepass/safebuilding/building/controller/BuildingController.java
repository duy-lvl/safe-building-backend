package com.safepass.safebuilding.building.controller;

import com.safepass.safebuilding.building.service.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/building")
public class BuildingController {
    @Autowired
    private BuildingService buildingService;
}
