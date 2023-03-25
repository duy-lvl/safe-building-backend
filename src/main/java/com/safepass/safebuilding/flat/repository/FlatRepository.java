package com.safepass.safebuilding.flat.repository;

import com.safepass.safebuilding.common.meta.FlatStatus;
import com.safepass.safebuilding.flat.entity.Flat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FlatRepository extends JpaRepository<Flat, UUID> {
   List<Flat> findFlatByBuildingIdAndStatusOrderByRoomNumberAsc(UUID buildingId, FlatStatus status);
   List<Flat> findFlatByBuildingId(UUID buildingId);

   int countFlatByBuildingId(UUID buildingId);
   Optional<Flat> findFlatByRoomNumberAndBuildingId(int roomNumber, UUID buildingId);
}
