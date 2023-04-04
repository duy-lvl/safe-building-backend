package com.safepass.safebuilding.bill_item.controller;

import com.safepass.safebuilding.bill_item.service.BillItemService;
import com.safepass.safebuilding.common.dto.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/bills/")
public class BillItemController {
    @Autowired
    private BillItemService billItemService;

    @GetMapping("/{billId}/bill-items")
    public ResponseEntity<ResponseObject> getBill(@PathVariable String billId) {
        return billItemService.getBillItem(billId);
    }
}
