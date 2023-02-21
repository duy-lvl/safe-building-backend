package com.safepass.safebuilding.building.controller;

import com.safepass.safebuilding.building.service.BuildingService;
import com.safepass.safebuilding.common.dto.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value = "/api/v1/buildings")
public class BuildingController {
    @Autowired
    private BuildingService buildingService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseObject> getBuildingList(
            @RequestParam(name = "page", defaultValue = "1")  int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        return buildingService.getBuildingList(page, size);
    }

    @GetMapping("/find-building")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseObject> findBuildingByName(
            @RequestParam(name = "page", defaultValue = "1")  int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "name") String name
    ) {
        return buildingService.searchBuildingByName(name, page, size);
    }
}
