package com.safepass.safebuilding.flat_type.entity;

import com.safepass.safebuilding.common.meta.FlatTypeStatus;
import com.safepass.safebuilding.flat.entity.Flat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
public class FlatType {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;

    private String name;
    private String description;
    private FlatTypeStatus status;

    @OneToMany(mappedBy = "flatTypeId")
    private List<Flat> flats;
}
