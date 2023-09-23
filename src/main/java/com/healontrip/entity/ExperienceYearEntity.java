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
@Table(name = "experience_years")
public class ExperienceYearEntity extends BaseEntity implements Serializable {
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "from_date", nullable = false)
    private int fromDate;

    @Column(name = "to_date")
    private int toDate;

    public ExperienceYearEntity(String name, int fromDate, int toDate) {
        this.name = name;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }
}
