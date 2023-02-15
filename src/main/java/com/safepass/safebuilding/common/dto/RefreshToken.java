package com.safepass.safebuilding.common.dto;

import com.safepass.safebuilding.admin.entity.Admin;
import com.safepass.safebuilding.common.meta.AdminStatus;
import com.safepass.safebuilding.common.meta.LoginAuthorities;
import com.safepass.safebuilding.customer.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefreshToken {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;

    @OneToOne
    @JoinColumn(name = "admin_id", referencedColumnName = "id")
    private Admin admin;

    @OneToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private Instant expiryDate;

    @Enumerated(EnumType.STRING)
    private LoginAuthorities loginAuthorities;
}
