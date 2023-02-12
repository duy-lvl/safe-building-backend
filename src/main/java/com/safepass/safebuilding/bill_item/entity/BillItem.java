package com.safepass.safebuilding.bill_item.entity;

import com.safepass.safebuilding.bill.entity.Bill;
import com.safepass.safebuilding.service.entity.Service;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BillItem {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;

    //FK
    @Type(type = "org.hibernate.type.UUIDCharType")
    @ManyToOne(targetEntity = Bill.class)
    @JoinColumn(
            name = "bill_id",
            referencedColumnName = "id"
    )
    private UUID billId;

    //FK
    @Type(type = "org.hibernate.type.UUIDCharType")
    @ManyToOne(targetEntity = Service.class)
    @JoinColumn(
            name = "service_id",
            referencedColumnName = "id"
    )
    private UUID serviceId;

    private int quantity;
    private int value;

}
