package com.scorer.service;

import com.scorer.entity.Sport;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class ScoreServiceTest {

    private ScoreService scoreService;

    @BeforeMethod
    public void setUp() {
        scoreService = new ScoreService(null);
    }

    @DataProvider(name = "scoreTestData")
    public Object[][] scoreTestData() {
        return new Object[][] {
                // sport, result, expectedPoints (approximate)
                { Sport.HUNDRED_METERS, 10.395, 1000 },
                { Sport.LONG_JUMP, 7.76, 1000 },
                { Sport.SHOT_PUT, 18.40, 1000 },
                { Sport.HIGH_JUMP, 2.20, 1000 },
                { Sport.FOUR_HUNDRED_METERS, 46.17, 1000 },
                { Sport.HURDLES_110M, 13.80, 1000 },
                { Sport.DISCUS_THROW, 56.17, 1000 },
                { Sport.POLE_VAULT, 5.28, 1000 },
                { Sport.JAVELIN_THROW, 77.19, 1000 },
                { Sport.FIFTEEN_HUNDRED_METERS, 233.79, 1000 },
        };
    }

    @Test(dataProvider = "scoreTestData")
    public void calculatePoints_knownValues_returnsExpectedPoints(Sport sport, double result, int expectedPoints) {
        // when
        int points = scoreService.calculatePoints(sport, result);

        // then
        int tolerance = 50;
        assertTrue(Math.abs(points - expectedPoints) <= tolerance,
                String.format("Expected ~%d points for %s with result %.2f, but got %d",
                        expectedPoints, sport.getDisplayName(), result, points));
    }

    @Test
    public void calculatePoints_longJump_7_76m_returns1000Points() {
        // given
        Sport sport = Sport.LONG_JUMP;
        double result = 7.76;

        // when
        int points = scoreService.calculatePoints(sport, result);

        // then
        assertEquals(points, 1000, "Long jump of 7.76m should give exactly 1000 points");
    }

    @Test
    public void calculatePoints_zeroResult_returnsZeroPoints() {
        // when
        int points = scoreService.calculatePoints(Sport.LONG_JUMP, 0);

        // then
        assertEquals(points, 0);
    }

    @Test
    public void calculatePoints_trackEvent_slowTime_returnsZeroPoints() {
        // when -> 100m in 20 seconds (very slow, exceeds B constant)
        int points = scoreService.calculatePoints(Sport.HUNDRED_METERS, 20);

        // then
        assertEquals(points, 0);
    }

}
