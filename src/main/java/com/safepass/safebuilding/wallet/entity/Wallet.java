package com.safepass.safebuilding.wallet.entity;

import com.safepass.safebuilding.common.meta.WalletStatus;
import com.safepass.safebuilding.customer.entity.Customer;
import com.safepass.safebuilding.money_transfer.entity.MoneyTransfer;
import com.safepass.safebuilding.transaction.entity.Transaction;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Wallet {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;

    //FK
    @ManyToOne
    private Customer customer;

    private int amount;

    @Enumerated(EnumType.STRING)
    private WalletStatus status;


}
