package com.safepass.safebuilding.flat.controller;

import com.safepass.safebuilding.common.dto.ResponseObject;
import com.safepass.safebuilding.common.exception.InvalidDataException;
import com.safepass.safebuilding.common.exception.InvalidPageSizeException;
import com.safepass.safebuilding.common.exception.MaxPageExceededException;
import com.safepass.safebuilding.common.exception.NoSuchDataException;
import com.safepass.safebuilding.flat.dto.RequestFlat;
import com.safepass.safebuilding.flat.service.FlatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/api/v1/flats")
public class FlatController {
    @Autowired
    private FlatService flatService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseObject> getFlatList(
            @RequestParam(name = "page", defaultValue = "1")  int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) throws InvalidPageSizeException, MaxPageExceededException, NoSuchDataException {
        return flatService.getFlatList(page, size);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseObject> createFlat(@RequestBody RequestFlat requestFlat)
            throws InvalidDataException {
        return flatService.createFlat(requestFlat);
    }
}
