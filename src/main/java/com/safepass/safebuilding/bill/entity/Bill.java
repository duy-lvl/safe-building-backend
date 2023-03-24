package com.safepass.safebuilding.bill.entity;

import com.safepass.safebuilding.common.meta.BillStatus;
import com.safepass.safebuilding.rent_contract.entity.RentContract;
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
@Entity
public class Bill {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;

    //FK
    @ManyToOne
    private RentContract rentContract;

    private Date date;
    private int value;

    @Enumerated(EnumType.STRING)
    private BillStatus status;
}
