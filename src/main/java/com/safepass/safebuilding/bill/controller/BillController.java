package com.safepass.safebuilding.bill.controller;

import com.safepass.safebuilding.bill.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/bill")
public class BillController {
    @Autowired
    private BillService billService;
}
