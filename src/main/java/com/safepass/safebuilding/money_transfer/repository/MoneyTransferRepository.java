package com.safepass.safebuilding.money_transfer.repository;

import com.safepass.safebuilding.money_transfer.entity.MoneyTransfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MoneyTransferRepository extends JpaRepository<MoneyTransfer, UUID> {
}
