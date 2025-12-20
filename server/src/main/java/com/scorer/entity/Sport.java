package com.scorer.entity;

public enum Sport {

    HUNDRED_METERS("100m", EventType.TRACK, 25.4347, 18, 1.81),
    LONG_JUMP("Long Jump", EventType.FIELD, 0.14354, 220, 1.40),
    SHOT_PUT("Shot Put", EventType.FIELD, 51.39, 1.5, 1.05),
    HIGH_JUMP("High Jump", EventType.FIELD, 0.8465, 75, 1.42),
    FOUR_HUNDRED_METERS("400m", EventType.TRACK, 1.53775, 82, 1.81),
    HURDLES_110M("110m Hurdles", EventType.TRACK, 5.74352, 28.5, 1.92),
    DISCUS_THROW("Discus Throw", EventType.FIELD, 12.91, 4, 1.1),
    POLE_VAULT("Pole Vault", EventType.FIELD, 0.2797, 100, 1.35),
    JAVELIN_THROW("Javelin Throw", EventType.FIELD, 10.14, 7, 1.08),
    FIFTEEN_HUNDRED_METERS("1500m", EventType.TRACK, 0.03768, 480, 1.85);

    private final String displayName;
    private final EventType eventType;
    private final double a;
    private final double b;
    private final double c;

    Sport(String displayName, EventType eventType, double a, double b, double c) {
        this.displayName = displayName;
        this.eventType = eventType;
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public String getDisplayName() {
        return displayName;
    }

    public EventType getEventType() {
        return eventType;
    }

    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }

    public double getC() {
        return c;
    }

    public static Sport fromDisplayName(String name) {
        for (Sport sport : values()) {
            if (sport.displayName.equalsIgnoreCase(name)) {
                return sport;
            }
        }
        return null;
    }

    public enum EventType {
        TRACK,
        FIELD
    }

}
