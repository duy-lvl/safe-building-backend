package com.safepass.safebuilding.building.repository;

import com.safepass.safebuilding.building.entity.Building;
import com.safepass.safebuilding.common.meta.BuildingStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BuildingRepository extends JpaRepository<Building, UUID> {
    Building findBuildingByNameAndAddress(String name, String address);
    Building findBuildingByNameAndAddressAndIdIsNotLike(String name, String address, UUID id);

    //    @Cacheable(cacheNames = "BuildingPaginationList")
    Page<Building> findAll(Pageable pageable);

    //    @Cacheable(cacheNames = "Building")
    Page<Building> findByNameContainsIgnoreCase(String name, Pageable pageable);

    List<Building> findByStatusOrderByNameAsc(BuildingStatus status);
    Optional<Building> findBuildingById(UUID id);

}
