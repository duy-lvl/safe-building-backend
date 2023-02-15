package com.safepass.safebuilding.money_transfer.controller;

import com.safepass.safebuilding.money_transfer.service.MoneyTransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/web/money-transfers")
public class MoneyTransferController {
    @Autowired
    private MoneyTransferService moneyTransferService;
}
