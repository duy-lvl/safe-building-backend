package com.safepass.safebuilding.flat_type.repository;

import com.safepass.safebuilding.flat_type.entity.FlatType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FlatTypeRepository extends JpaRepository<FlatType, UUID> {
}
