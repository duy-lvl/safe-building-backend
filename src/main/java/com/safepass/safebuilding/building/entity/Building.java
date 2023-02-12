package com.safepass.safebuilding.building.entity;

import com.safepass.safebuilding.flat.entity.Flat;
import com.safepass.safebuilding.common.meta.BuildingStatus;
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
public class Building {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;

    private String name;

    @Enumerated(EnumType.STRING)
    private BuildingStatus status;

    @OneToMany(mappedBy = "id", fetch = FetchType.EAGER)
    private List<Flat> flats;

}
