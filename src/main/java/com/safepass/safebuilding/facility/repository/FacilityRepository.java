package com.safepass.safebuilding.facility.repository;

import com.safepass.safebuilding.facility.entity.Facility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FacilityRepository extends JpaRepository<Facility, UUID> {
}
