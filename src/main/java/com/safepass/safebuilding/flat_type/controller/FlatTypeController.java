package com.safepass.safebuilding.flat_type.controller;

import com.safepass.safebuilding.flat_type.service.FlatTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/web/flat-types")
public class FlatTypeController {
    @Autowired
    private FlatTypeService flatTypeService;
}
