package com.safepass.safebuilding.flat.entity;

import com.safepass.safebuilding.flat_type.entity.FlatType;
import com.safepass.safebuilding.building.entity.Building;
import com.safepass.safebuilding.facility.entity.Facility;
import com.safepass.safebuilding.rent_contract.entity.RentContract;
import com.safepass.safebuilding.common.meta.FlatStatus;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.UUID;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
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

    @Min(101)
    @Max(9999)
    private int roomNumber;
    //FK
    @ManyToOne
    private FlatType flatType;

    @ManyToOne
    private Building building;

    @ManyToMany
    @JoinTable(
            name = "flat_facility",
            joinColumns = @JoinColumn(name = "flat_id"),
            inverseJoinColumns = @JoinColumn(name = "facility_id")
    )
    private List<Facility> facilities;

}
