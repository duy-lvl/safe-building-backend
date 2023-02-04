package com.safepass.safebuilding.transaction.entity;

import com.safepass.safebuilding.wallet.entity.Wallet;
import com.safepass.safebuilding.common.meta.TransactionStatus;
import com.safepass.safebuilding.common.meta.TransactionType;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.sql.Date;
import java.util.UUID;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Transaction {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;

    //FK
    @Type(type = "org.hibernate.type.UUIDCharType")
    @ManyToOne(targetEntity = Wallet.class)
    @JoinColumn(
            name = "wallet_id",
            referencedColumnName = "id"
    )
    private UUID walletId;

    private Date date;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    private int amount;
    private String description;

    @Enumerated(EnumType.STRING)
    private TransactionStatus status;


}
