package com.safepass.safebuilding.transaction.repository;


import com.safepass.safebuilding.transaction.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    Page<Transaction> findTransactionByBillIdOrderByDateDesc(UUID billId, Pageable pageable);
}
