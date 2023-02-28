package com.safepass.safebuilding.bill_item.controller;

import com.safepass.safebuilding.bill_item.service.BillItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/bills/bill-items")
public class BillItemController {
    @Autowired
    private BillItemService billItemService;
}
