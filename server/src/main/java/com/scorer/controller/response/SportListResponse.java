package com.scorer.controller.response;

import com.scorer.entity.Sport;

import java.util.List;

public record SportListResponse(
        List<SportItem> content
) {
    public static SportListResponse from(List<Sport> sports) {
        return new SportListResponse(
                sports.stream().map(SportItem::from).toList()
        );
    }

    public record SportItem(
            String name,
            String displayName,
            String eventType
    ) {
        public static SportItem from(Sport sport) {
            return new SportItem(
                    sport.name(),
                    sport.getDisplayName(),
                    sport.getEventType().name()
            );
        }
    }
}
