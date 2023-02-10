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
public class Wallet {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;

    //FK
    @Type(type = "org.hibernate.type.UUIDCharType")
    @ManyToOne(targetEntity = Customer.class)
    @JoinColumn(
            name = "customer_id",
            referencedColumnName = "id"
    )
    private UUID customerId;

    private int amount;

    @Enumerated(EnumType.STRING)
    private WalletStatus status;

    @OneToMany(mappedBy = "id")
    private List<Transaction> transactions;

    @OneToMany(mappedBy = "id")
    private List<MoneyTransfer> moneyTransfers;
}
