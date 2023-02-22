package com.safepass.safebuilding.building.repository;

import com.safepass.safebuilding.building.entity.Building;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BuildingRepository extends JpaRepository<Building, UUID> {
    @Cacheable(cacheNames = "BuildingPaginationList")
    Page<Building> findAll(Pageable pageable);

    @Cacheable(cacheNames = "Building")
    Page<Building> findByNameContainsIgnoreCase(String name, Pageable pageable);
}
