package com.safepass.safebuilding.bill.controller;

import com.safepass.safebuilding.bill.dto.BillCreate;
import com.safepass.safebuilding.bill.service.BillService;
import com.safepass.safebuilding.common.dto.ResponseObject;
import com.safepass.safebuilding.common.exception.InvalidPageSizeException;
import com.safepass.safebuilding.common.exception.MaxPageExceededException;
import com.safepass.safebuilding.common.exception.NoSuchDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController

@RequestMapping(value = "/api/v1/bills")
public class BillController {
    @Autowired
    private BillService billService;

    @GetMapping("/{customerId}")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<ResponseObject> getBillList(
            @RequestParam(name = "page", defaultValue = "1")  int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @PathVariable(name = "customerId") String customerId
    ) throws InvalidPageSizeException, MaxPageExceededException, NoSuchDataException {
        return billService.getBillList(customerId, page, size);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseObject> getBillList(
            @RequestParam(name = "page", defaultValue = "1")  int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) throws InvalidPageSizeException, MaxPageExceededException, NoSuchDataException {
        return billService.getBillList(page, size);
    }
    @PostMapping("/bill")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseObject> createBill(@RequestBody BillCreate billCreate) throws NoSuchDataException {
        return billService.createBill(billCreate);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseObject> createBill() {
        return billService.createBill();
    }
}
