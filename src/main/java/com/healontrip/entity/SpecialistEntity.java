package com.healontrip.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.io.Serializable;

@Data
@NoArgsConstructor
@Builder
@Log4j2

@Entity
@Table(name = "specialist")
public class SpecialistEntity extends BaseEntity implements Serializable {
    @Column(name = "name", nullable = false)
    private String name;

    public SpecialistEntity(String name) {
        this.name = name;
    }
}
