package com.safepass.safebuilding.rent_contract.repository;

import com.safepass.safebuilding.common.meta.RentContractStatus;
import com.safepass.safebuilding.rent_contract.entity.RentContract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RentContractRepository extends JpaRepository<RentContract, UUID> {
    List<RentContract> findByCustomerId(UUID customerId);

    Optional<RentContract> findRentContractById(UUID id);

    Optional<RentContract> findRentContractByFlatIdAndStatus(UUID id, RentContractStatus flatStatus);

    List<RentContract> findAllByStatus(RentContractStatus status);
}
