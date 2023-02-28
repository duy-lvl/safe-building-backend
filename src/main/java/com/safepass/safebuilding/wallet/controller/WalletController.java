package com.safepass.safebuilding.wallet.controller;

import com.safepass.safebuilding.common.dto.ResponseObject;
import com.safepass.safebuilding.wallet.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/web/wallets")
public class WalletController {
    @Autowired
    private WalletService walletService;

    @GetMapping("/{customerId}")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<ResponseObject> getWallet(@PathVariable String customerId) {
        return walletService.getWallet(customerId);
    }
}
