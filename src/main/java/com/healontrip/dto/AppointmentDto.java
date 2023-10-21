package com.healontrip.dto;

import com.healontrip.constraint.BooleanNotNullConstraint;
import com.healontrip.constraint.PhoneNumberNotNullConstraint;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Log4j2
public class AppointmentDto {
    private Long id;

    @NotNull(message = "Preferred communication method must have value")
    private Long communicationId;

    @NotEmpty(message = "Short explanation method must have value")
    private String shortExplanation;

    @BooleanNotNullConstraint(message = "Terms & Conditions must be accepted")
    private Boolean termsAccept;

    @PhoneNumberNotNullConstraint(message = "Phone number method must have value")
    private String patientPhoneNumber;

    private Long doctorId;
    private Long patientId;

}
