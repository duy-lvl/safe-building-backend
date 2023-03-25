package com.safepass.safebuilding.transaction.entity;

import com.safepass.safebuilding.bill.entity.Bill;
import com.safepass.safebuilding.wallet.entity.Wallet;
import com.safepass.safebuilding.common.meta.TransactionStatus;
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


    private Date date;

    private int amount;
    private String description;

    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    @OneToOne
    private Bill bill;

    private String type;
    private int accountBalance;

    @ManyToOne
    private Wallet wallet;
}
