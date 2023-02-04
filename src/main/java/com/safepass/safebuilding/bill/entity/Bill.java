package com.safepass.safebuilding.bill.entity;

import com.safepass.safebuilding.bill_item.entity.BillItem;
import com.safepass.safebuilding.flat.entity.Flat;
import com.safepass.safebuilding.common.meta.BillStatus;
import com.safepass.safebuilding.rent_contract.entity.RentContract;
import com.safepass.safebuilding.transaction.entity.Transaction;
import com.safepass.safebuilding.wallet.entity.Wallet;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;
import java.util.UUID;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Bill {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;

    //FK
    @Type(type = "org.hibernate.type.UUIDCharType")
    @ManyToOne(targetEntity = RentContract.class)
    @JoinColumn(
        name = "rent_contract_id",
        referencedColumnName = "id"
    )
    private UUID rentContractId;

    private Date date;
    private int value;

    @Enumerated(EnumType.STRING)
    private BillStatus status;

    @OneToMany(mappedBy = "billId")
    private List<BillItem> billItems;

    //FK
    @OneToOne(targetEntity = Transaction.class)
    @JoinColumn(
            name = "transaction_id",
            referencedColumnName = "id"
    )
    private UUID transactionId;
}
