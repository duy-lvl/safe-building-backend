package com.safepass.safebuilding.wallet.service.impl;

import com.safepass.safebuilding.wallet.entity.Wallet;

public class WalletServiceUtil {
    public static String createInsert(Wallet wallet) {
        return "INSERT INTO wallet(id, customer_id, amount, status) VALUES ('"
                + wallet.getId() + "', '"
                + wallet.getCustomer().getId() + "', "
                + wallet.getAmount() + ", '"
                + wallet.getStatus() + "')";
    }
}
