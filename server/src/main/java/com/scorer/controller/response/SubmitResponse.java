package com.scorer.controller.response;

import com.scorer.entity.ScoreResult;

public record SubmitResponse(
        String id,
        String sport,
        Double result,
        Integer points
) {
    public static SubmitResponse from(ScoreResult scoreResult) {
        return new SubmitResponse(
                scoreResult.getId(),
                scoreResult.getSport().getDisplayName(),
                scoreResult.getResult(),
                scoreResult.getPoints()
        );
    }
}
