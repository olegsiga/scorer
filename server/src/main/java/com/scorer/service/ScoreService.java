package com.scorer.service;

import com.scorer.dao.ScoreResultDao;
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

    public ScoreResult submitScore(Sport sport, double result) {
        int points = calculatePoints(sport, result);

        var scoreResult = new ScoreResult()
                .setId(generateId())
                .setSport(sport)
                .setResult(result)
                .setPoints(points)
                .setCreatedAt(Instant.now());

        scoreResultDao.insert(scoreResult);

        return scoreResult;
    }

    public int calculatePoints(Sport sport, double result) {
        double a = sport.getA();
        double b = sport.getB();
        double c = sport.getC();

        double points;
        if (sport.getEventType() == Sport.EventType.TRACK) {
            // Track events: Points = A × (B − P)^C
            // P is time in seconds
            double p = convertToSeconds(sport, result);
            if (p >= b) {
                return 0;
            }
            points = a * Math.pow(b - p, c);
        } else {
            // Field events: Points = A × (P − B)^C
            // P is distance in cm (for jumps) or meters (for throws)
            double p = convertToFieldUnit(sport, result);
            if (p <= b) {
                return 0;
            }
            points = a * Math.pow(p - b, c);
        }

        return (int) Math.floor(points);
    }

    private double convertToSeconds(Sport sport, double result) {
        // For 1500m, result might be in format like 233.79 (3:53.79 = 233.79 seconds)
        // For other track events, result is already in seconds
        return result;
    }

    private double convertToFieldUnit(Sport sport, double result) {
        // Long Jump, High Jump, Pole Vault: result in meters, formula uses cm
        // Shot Put, Discus, Javelin: result in meters, formula uses meters
        return switch (sport) {
            case LONG_JUMP, HIGH_JUMP, POLE_VAULT -> result * 100; // convert m to cm
            default -> result; // already in meters
        };
    }

    private String generateId() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 16);
    }

}
