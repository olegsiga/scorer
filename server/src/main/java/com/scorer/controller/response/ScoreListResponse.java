package com.scorer.controller.response;

import com.scorer.entity.ScoreResult;

import java.util.List;

public record ScoreListResponse(
        List<ScoreItem> content
) {
    public static ScoreListResponse from(List<ScoreResult> results) {
        return new ScoreListResponse(
                results.stream().map(ScoreItem::from).toList()
        );
    }

    public record ScoreItem(
            String id,
            String sport,
            Double result,
            Integer points,
            String createdAt
    ) {
        public static ScoreItem from(ScoreResult scoreResult) {
            return new ScoreItem(
                    scoreResult.getId(),
                    scoreResult.getSport().getDisplayName(),
                    scoreResult.getResult(),
                    scoreResult.getPoints(),
                    scoreResult.getCreatedAt().toString()
            );
        }
    }
}
