package com.safepass.safebuilding.building.entity;

import com.safepass.safebuilding.flat.entity.Flat;
import com.safepass.safebuilding.common.meta.BuildingStatus;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
public class Building implements Serializable {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;

    private String name;
    private String address;


    @Enumerated(EnumType.STRING)
    private BuildingStatus status;

    @Column(columnDefinition = "int default -1")
    private int capacity;

}
