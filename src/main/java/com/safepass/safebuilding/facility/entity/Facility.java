package com.safepass.safebuilding.facility.entity;

import com.safepass.safebuilding.common.meta.FurnitureStatus;
import com.safepass.safebuilding.flat.entity.Flat;
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
@ToString
@Entity
public class Facility {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;

    //FK
    @Type(type = "org.hibernate.type.UUIDCharType")
    @ManyToOne(targetEntity = Flat.class)
    @JoinColumn(
            name = "flat_id",
            referencedColumnName = "id"
    )
    private UUID flatId;

    private String item;
    private int quantity;

    @Enumerated(EnumType.STRING)
    private FurnitureStatus status;

    @ManyToMany(mappedBy = "facilities")
    private List<Flat> flats;
}
