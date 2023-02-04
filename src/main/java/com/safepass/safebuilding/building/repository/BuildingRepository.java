package com.safepass.safebuilding.building.repository;

import com.safepass.safebuilding.building.entity.Building;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BuildingRepository extends JpaRepository<Building, UUID> {
}
