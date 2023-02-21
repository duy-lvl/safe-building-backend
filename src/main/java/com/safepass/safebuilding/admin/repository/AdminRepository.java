package com.safepass.safebuilding.admin.repository;

import com.safepass.safebuilding.admin.entity.Admin;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AdminRepository extends JpaRepository<Admin, UUID> {

    Admin findAdminByEmail(String email);
    @Cacheable(cacheNames = "admin")
    Admin findAdminByPhone(String phone);
    @Query("SELECT a FROM Admin a WHERE a.email = ?1 OR a.phone = ?1 AND a.password = ?2")
    Admin loginAdmin(String phoneOrEmail, String password);

    Admin findAdminByPhoneAndPassword(String phone, String password);

}
