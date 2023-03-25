package com.safepass.safebuilding.flat_type.controller;

import com.safepass.safebuilding.common.dto.ResponseObject;
import com.safepass.safebuilding.flat_type.dto.FlatTypeCreateDTO;
import com.safepass.safebuilding.flat_type.entity.FlatType;
import com.safepass.safebuilding.flat_type.service.FlatTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/flat-types")
public class FlatTypeController {
    @Autowired
    private FlatTypeService flatTypeService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseObject> getFlatTypeList() {
        return flatTypeService.getFlatType();
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseObject> create(@RequestBody FlatTypeCreateDTO flatType) {
        return flatTypeService.createFlatType(flatType);
    }
}
