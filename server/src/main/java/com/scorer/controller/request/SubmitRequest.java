package com.scorer.controller.request;

import com.scorer.entity.Sport;
import com.scorer.validation.ValidSport;
import jakarta.validation.constraints.Positive;

public record SubmitRequest(
        @ValidSport String sport,
        @Positive double result
) {
    public Sport toSport() {
        return Sport.fromDisplayName(sport);
    }
}
