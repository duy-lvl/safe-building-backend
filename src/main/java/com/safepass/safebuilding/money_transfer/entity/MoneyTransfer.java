package com.safepass.safebuilding.money_transfer.entity;

import com.safepass.safebuilding.common.meta.MoneyTransferStatus;
import com.safepass.safebuilding.common.meta.MoneyTransferType;
import com.safepass.safebuilding.customer.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.sql.Date;
import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class MoneyTransfer {
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

    @Enumerated(EnumType.STRING)
    private MoneyTransferType type;

    private int amount;
    private Date date;

    @Enumerated(EnumType.STRING)
    private MoneyTransferStatus status;
}
