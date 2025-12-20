package com.scorer.controller.response;

import com.scorer.entity.ScoreResult;

public record SubmitResponse(
        String status,
        String id,
        String sport,
        Double result,
        Integer points
) {
    public static SubmitResponse ok(ScoreResult scoreResult) {
        return new SubmitResponse(
                "ok",
                scoreResult.getId(),
                scoreResult.getSport().getDisplayName(),
                scoreResult.getResult(),
                scoreResult.getPoints()
        );
    }

    public static SubmitResponse error(String status) {
        return new SubmitResponse(status, null, null, null, null);
    }
}
