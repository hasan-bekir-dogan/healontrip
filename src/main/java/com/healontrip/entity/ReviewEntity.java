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
@Table(name = "reviews")
public class ReviewEntity extends BaseEntity implements Serializable {
    @Column(name = "doctor_id", nullable = false)
    private Long doctorId;

    @Column(name = "patient_id", nullable = false)
    private Long patientId;

    @Column(name = "rating", nullable = false)
    private int rating;

    @Column(name = "detail", length = 4000)
    private String detail;

    public ReviewEntity(Long doctorId, Long patientId, int rating, String detail) {
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.rating = rating;
        this.detail = detail;
    }
}
