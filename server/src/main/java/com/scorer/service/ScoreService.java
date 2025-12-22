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
            double distance = convertToFieldUnit(sport, measurement);
            if (distance <= sport.getBaseline()) {
                return 0;
            }
            double difference = distance - sport.getBaseline();
            return applyFormula(sport, difference);
        }
    }

    private int applyFormula(Sport sport, double difference) {
        double points = sport.getCoefficient() * Math.pow(difference, sport.getExponent());
        return Double.valueOf(Math.floor(points)).intValue();
    }

    private double convertToFieldUnit(Sport sport, double measurement) {
        // Long Jump, High Jump, Pole Vault: measurement in meters, formula uses cm
        // Shot Put, Discus, Javelin: measurement in meters, formula uses meters
        return switch (sport) {
            case LONG_JUMP, HIGH_JUMP, POLE_VAULT -> measurement * 100; // convert m to cm
            default -> measurement; // already in meters
        };
    }

    private String generateId() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 16);
    }

}
