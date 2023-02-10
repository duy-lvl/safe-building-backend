package com.safepass.safebuilding.admin.repository;

import com.safepass.safebuilding.admin.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AdminRepository extends JpaRepository<Admin, UUID> {
    Admin findByUsername(String username);

    boolean existsByUsernameAndPassword(String username, String password);
    Admin findAdminByUsername(String username);
}
