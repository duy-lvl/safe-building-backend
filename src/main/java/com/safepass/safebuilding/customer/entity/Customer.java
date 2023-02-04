package com.safepass.safebuilding.customer.entity;

import com.safepass.safebuilding.money_transfer.entity.MoneyTransfer;
import com.safepass.safebuilding.notification.entity.Notification;
import com.safepass.safebuilding.rent_contract.entity.RentContract;
import com.safepass.safebuilding.common.meta.CustomerStatus;
import com.safepass.safebuilding.common.meta.Gender;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.sql.Date;
import java.util.List;
import java.util.UUID;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Customer {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;

    private String username;
    private String password;

    @Column(columnDefinition = "varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL, FULLTEXT KEY name(name)")
    private String fullname;

    private Date dateOfBirth;
    private Gender gender;

    @Email
    private String email;

    private String phone;
    private String address;
    private String citizenId;
    private Date dateJoin;

    @Enumerated(EnumType.STRING)
    private CustomerStatus status;

    @OneToMany(mappedBy = "customerId")
    private List<RentContract> rentContracts;

    @OneToMany(mappedBy = "customerId")
    private List<MoneyTransfer> moneyTransfers;

    @OneToMany(mappedBy = "customerId")
    private List<Notification> notifications;
}
