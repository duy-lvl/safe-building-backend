package com.safepass.safebuilding.transaction.controller;

import com.safepass.safebuilding.transaction.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/web/transactions")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;
}
