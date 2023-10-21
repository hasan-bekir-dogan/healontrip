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
@Table(name = "appointments")
public class AppointmentEntity extends BaseEntity implements Serializable {
    @Column(name = "doctor_id", nullable = false)
    private Long doctorId;

    @Column(name = "patient_id", nullable = false)
    private Long patientId;

    @Column(name = "communication_id", nullable = false)
    private Long communicationId;

    @Column(name = "short_explanation", nullable = false)
    private String shortExplanation;

    public AppointmentEntity(Long doctorId, Long patientId, Long communicationId, String shortExplanation) {
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.communicationId = communicationId;
        this.shortExplanation = shortExplanation;
    }
}
