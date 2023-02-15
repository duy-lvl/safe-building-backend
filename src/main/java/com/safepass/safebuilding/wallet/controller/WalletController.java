package com.safepass.safebuilding.wallet.controller;

import com.safepass.safebuilding.wallet.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/web/wallets")
public class WalletController {
    @Autowired
    private WalletService walletService;
}
