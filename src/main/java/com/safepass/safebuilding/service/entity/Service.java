package com.safepass.safebuilding.service.entity;

import com.safepass.safebuilding.bill_item.entity.BillItem;
import com.safepass.safebuilding.common.meta.ServiceStatus;
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
public class Service {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;

    private String name;
    private String description;
    private int price;

    @Enumerated(EnumType.STRING)
    private ServiceStatus status;

    @OneToMany(mappedBy = "id")
    private List<BillItem> billItems;
}
