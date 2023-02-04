package com.safepass.safebuilding.furniture.controller;

import com.safepass.safebuilding.furniture.service.FurnitureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/furniture")
public class FurnitureController {
    @Autowired
    private FurnitureService furnitureService;
}
