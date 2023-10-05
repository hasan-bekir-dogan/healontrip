package com.healontrip.dto;

import com.healontrip.constraint.BooleanNotNullConstraint;
import com.healontrip.constraint.IntegerNotNullConstraint;
import jakarta.validation.constraints.NotEmpty;
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
public class ReviewDto {
    private Long id;

    @IntegerNotNullConstraint(message = "Rating Star must have value")
    private int rating;

    private String detail;

    @BooleanNotNullConstraint(message = "Terms & Conditions must be accepted")
    private Boolean termsAccept;
    private Long userId;
}
