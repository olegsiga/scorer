package com.scorer.service;

import com.scorer.dao.ScoreResultDao;
import com.scorer.entity.Performance;
import com.scorer.entity.ScoreResult;
import com.scorer.entity.Sport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ScoreService {

    private static final int ZERO = 0;

    private final ScoreResultDao scoreResultDao;

    public ScoreResult submitScore(Performance performance) {
        int points = calculatePoints(performance);

        var scoreResult = new ScoreResult()
                .setId(generateId())
                .setSport(performance.sport())
                .setResult(performance.measurement())
                .setPoints(points)
                .setCreatedAt(Instant.now());

        scoreResultDao.insert(scoreResult);

        return scoreResult;
    }

    public int calculatePoints(Performance performance) {
        Sport sport = performance.sport();
        double measurement = performance.measurement();

        if (sport.getEventType() == Sport.EventType.TRACK) {
            if (measurement >= sport.getBaseline()) {
                return 0;
            }
            // Track events: Points = coefficient × (baseline − measurement)^exponent
            double difference = sport.getBaseline() - measurement;
            return applyFormula(sport, difference);
        } else {
            // Field events: Points = coefficient × (measurement − baseline)^exponent
            double distance = sport.toScoringUnit(measurement);
            if (distance <= sport.getBaseline()) {
                return ZERO;
            }
            double difference = distance - sport.getBaseline();
            return applyFormula(sport, difference);
        }
    }

    private int applyFormula(Sport sport, double difference) {
        double points = sport.getCoefficient() * Math.pow(difference, sport.getExponent());
        return Double.valueOf(Math.floor(points)).intValue();
    }

    private String generateId() {
        return UUID.randomUUID().toString();
    }

}
