package com.safepass.safebuilding.common.repository;

import com.safepass.safebuilding.admin.entity.Admin;
import com.safepass.safebuilding.common.dto.RefreshToken;
import com.safepass.safebuilding.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {
    Optional<RefreshToken> findByToken(String token);

    @Modifying
    int deleteByAdmin(Admin admin);

    @Modifying
    int deleteByCustomer(Customer customer);
}
