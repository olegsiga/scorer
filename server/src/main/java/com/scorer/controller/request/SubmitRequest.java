package com.scorer.controller.request;

import jakarta.validation.constraints.NotBlank;

public record SubmitRequest(
        @NotBlank String sport,
        double result
) {}
