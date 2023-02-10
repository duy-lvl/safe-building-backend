package com.safepass.safebuilding.rent_contract.entity;

import com.safepass.safebuilding.bill.entity.Bill;
import com.safepass.safebuilding.common.meta.RentContractStatus;
import com.safepass.safebuilding.customer.entity.Customer;
import com.safepass.safebuilding.flat.entity.Flat;
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
@ToString
@Entity
public class RentContract {
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

    //FK
    @Type(type = "org.hibernate.type.UUIDCharType")
    @ManyToOne(targetEntity = Flat.class)
    @JoinColumn(
            name = "flat_id",
            referencedColumnName = "id"
    )
    private UUID flatId;

    private String contract;
    private Date expiryDate;
    private int value;

    @Enumerated(EnumType.STRING)
    private RentContractStatus status;

    @OneToMany(mappedBy = "id")
    private List<Bill> bills;
}
