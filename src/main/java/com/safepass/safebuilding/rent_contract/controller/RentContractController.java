package com.safepass.safebuilding.rent_contract.controller;

import com.safepass.safebuilding.common.dto.ResponseObject;
import com.safepass.safebuilding.common.exception.InvalidDataException;
import com.safepass.safebuilding.common.exception.InvalidPageSizeException;
import com.safepass.safebuilding.common.exception.MaxPageExceededException;
import com.safepass.safebuilding.common.exception.NoSuchDataException;
import com.safepass.safebuilding.rent_contract.service.RentContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;

@RestController
@RequestMapping(value = "/api/v1/rent-contracts")
public class RentContractController {
    @Autowired
    private RentContractService rentContractService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseObject> getList(
            @RequestParam(name = "page", defaultValue = "1")  int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) throws InvalidPageSizeException, MaxPageExceededException, NoSuchDataException {
        return rentContractService.getList(page, size);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseObject> getById(@PathVariable String id) {
        return rentContractService.getContractById(id);
    }

    @PostMapping("/create-contract")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseObject> createContract(
            @RequestParam String requestContract,
            @RequestParam String deviceToken,
            @RequestParam MultipartFile[] files
    ) throws IOException, SQLException, InvalidDataException {
        return rentContractService.createContract(files, requestContract, deviceToken);
    }

    @DeleteMapping("/delete-contract/{contractId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseObject> deleteContract(@PathVariable String contractId) throws NoSuchDataException {
        return rentContractService.deleteContract(contractId);
    }
}
