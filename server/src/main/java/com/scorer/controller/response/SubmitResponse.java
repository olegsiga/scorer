package com.scorer.controller.response;

import com.scorer.entity.ScoreResult;

public record SubmitResponse(
        String status,
        String id,
        String sport,
        Double result,
        Integer points
) {
    public static final String STATUS_OK = "ok";
    public static final String STATUS_INVALID_SPORT = "invalid-sport";
    public static final String STATUS_INVALID_RESULT = "invalid-result";

    public static SubmitResponse ok(ScoreResult scoreResult) {
        return new SubmitResponse(
                STATUS_OK,
                scoreResult.getId(),
                scoreResult.getSport().getDisplayName(),
                scoreResult.getResult(),
                scoreResult.getPoints()
        );
    }

    public static SubmitResponse invalidSport() {
        return new SubmitResponse(STATUS_INVALID_SPORT, null, null, null, null);
    }

    public static SubmitResponse invalidResult() {
        return new SubmitResponse(STATUS_INVALID_RESULT, null, null, null, null);
    }
}
