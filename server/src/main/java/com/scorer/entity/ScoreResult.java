package com.scorer.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.Instant;

@Getter
@Setter
@Accessors(chain = true)
public class ScoreResult {

    private String id;
    private Sport sport;
    private double result;
    private int points;
    private Instant createdAt;

}
