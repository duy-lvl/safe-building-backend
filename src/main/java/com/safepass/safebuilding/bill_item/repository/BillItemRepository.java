package com.safepass.safebuilding.bill_item.repository;

import com.safepass.safebuilding.bill_item.entity.BillItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BillItemRepository extends JpaRepository<BillItem, UUID> {
}
