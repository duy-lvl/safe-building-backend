package com.safepass.safebuilding.wallet.service.impl;

import com.safepass.safebuilding.common.dto.ResponseObject;
import com.safepass.safebuilding.common.utils.ModelMapperCustom;
import com.safepass.safebuilding.wallet.dto.WalletDTO;
import com.safepass.safebuilding.wallet.entity.Wallet;
import com.safepass.safebuilding.wallet.repository.WalletRepository;
import com.safepass.safebuilding.wallet.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class WalletServiceImpl implements WalletService {
    @Autowired
    private WalletRepository walletRepository;
    private ModelMapperCustom modelMapper = new ModelMapperCustom();
    @Override
    public ResponseEntity<ResponseObject> getWallet(String customerId) {
        List<Wallet> wallets = walletRepository.findByCustomerId(UUID.fromString(customerId));
        List<WalletDTO> walletDTOS = modelMapper.mapList(wallets, WalletDTO.class);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.OK.toString(), "Successfully", null, walletDTOS));
    }
}
