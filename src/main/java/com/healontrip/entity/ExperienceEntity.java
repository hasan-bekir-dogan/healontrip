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
@Table(name = "experiences")
public class ExperienceEntity extends BaseEntity implements Serializable {
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "hospital_name", nullable = false)
    private String hospitalName;

    @Column(name = "designation", nullable = false)
    private String designation;

    @Column(name = "from_date", nullable = false)
    private String fromDate;

    @Column(name = "to_date")
    private String toDate;

    public ExperienceEntity(Long userId, String hospitalName, String designation, String fromDate, String toDate) {
        this.userId = userId;
        this.hospitalName = hospitalName;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.designation = designation;
    }
}