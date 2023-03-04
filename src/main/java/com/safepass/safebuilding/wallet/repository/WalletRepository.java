package com.safepass.safebuilding.wallet.repository;

import com.safepass.safebuilding.common.meta.WalletStatus;
import com.safepass.safebuilding.wallet.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, UUID> {
    List<Wallet> findByCustomerId(UUID customerId);
    @Query(value = "INSERT INTO wallet(id, customer_id, amount, status) VALUES (?1,?2,?3,?4)", nativeQuery = true)
    @Modifying
    int saveWallet(UUID id, UUID customer_id, int amount, WalletStatus status);
}
