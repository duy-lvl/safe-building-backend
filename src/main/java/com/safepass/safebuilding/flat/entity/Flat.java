package com.safepass.safebuilding.flat.entity;

import com.safepass.safebuilding.flat_type.entity.FlatType;
import com.safepass.safebuilding.building.entity.Building;
import com.safepass.safebuilding.facility.entity.Facility;
import com.safepass.safebuilding.rent_contract.entity.RentContract;
import com.safepass.safebuilding.common.meta.FlatStatus;
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
public class Flat {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;

    private String detail;
    private int price;

    @Enumerated(EnumType.STRING)
    private FlatStatus status;

    //FK
    @Type(type = "org.hibernate.type.UUIDCharType")
    @ManyToOne(targetEntity = FlatType.class)
    @JoinColumn(
            name = "flat_type_id",
            referencedColumnName = "id"
    )
    private UUID flatTypeId;

    @Type(type = "org.hibernate.type.UUIDCharType")
    @ManyToOne(targetEntity = Building.class)
    @JoinColumn(
            name = "building_id",
            referencedColumnName = "id"
    )
    private UUID buildingId;



    @OneToMany(mappedBy = "id")
    private List<RentContract> rentContracts;

    @ManyToMany
    @JoinTable(
            name = "flat_facility",
            joinColumns = @JoinColumn(name = "flat_id"),
            inverseJoinColumns = @JoinColumn(name = "facility_id")
    )
    private List<Facility> facilities;
}
