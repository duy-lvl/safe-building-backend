package com.safepass.safebuilding.rent_contract.repository;

import com.safepass.safebuilding.rent_contract.entity.RentContract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RentContractRepository extends JpaRepository<RentContract, UUID> {
    List<RentContract> findByCustomerId(UUID customerId);
}
