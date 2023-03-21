package com.safepass.safebuilding.bill.repository;

import com.safepass.safebuilding.bill.entity.Bill;
import com.safepass.safebuilding.common.meta.BillStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BillRepository extends JpaRepository<Bill, UUID> {
    List<Bill> findByStatus(BillStatus status);

    List<Bill> findBillByRentContractId(UUID id);

    Page<Bill> findAllByOrderByDateDesc(Pageable pageable);
}
