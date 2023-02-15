package com.safepass.safebuilding.flat.controller;

import com.safepass.safebuilding.flat.service.FlatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/web/flats")
public class FlatController {
    @Autowired
    private FlatService flatService;
}
