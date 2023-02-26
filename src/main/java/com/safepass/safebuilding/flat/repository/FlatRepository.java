package com.safepass.safebuilding.flat.repository;

import com.safepass.safebuilding.common.meta.FlatStatus;
import com.safepass.safebuilding.flat.entity.Flat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FlatRepository extends JpaRepository<Flat, UUID> {
    @Modifying
    @Query(value = "UPDATE Flat SET status = ?1 WHERE id = ?2")
    void updateStatus(FlatStatus status, UUID id);
}
