package com.safepass.safebuilding.wallet.service;

import com.safepass.safebuilding.common.dto.ResponseObject;
import org.springframework.http.ResponseEntity;

public interface WalletService {
    ResponseEntity<ResponseObject> getWallet(String customerId);
}
