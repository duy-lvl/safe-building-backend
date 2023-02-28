package com.safepass.safebuilding.service.repository;

import com.safepass.safebuilding.common.meta.ServiceStatus;
import com.safepass.safebuilding.service.entity.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ServiceRepository extends JpaRepository<Service, UUID> {
    Page<Service> findByStatus(ServiceStatus status, Pageable pageable);
}
