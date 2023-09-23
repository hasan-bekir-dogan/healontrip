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
@Table(name = "educations")
public class EducationEntity extends BaseEntity implements Serializable {
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "degree", nullable = false)
    private String degree;

    @Column(name = "university", nullable = false)
    private String university;

    @Column(name = "from_date", nullable = false)
    private String fromDate;

    @Column(name = "to_date")
    private String toDate;

    public EducationEntity(Long userId, String degree, String university, String fromDate, String toDate) {
        this.userId = userId;
        this.degree = degree;
        this.university = university;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }
}
