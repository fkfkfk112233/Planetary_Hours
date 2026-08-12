package com.example.Planetary_hours.dto;

import java.time.LocalTime;

public class PlanetaryHourResponse {

    private final int hour;

    private final String type;

    private final String planet;

    private final LocalTime start;

    private final LocalTime end;

    public PlanetaryHourResponse(
            int hour,
            String type,
            String planet,
            LocalTime start,
            LocalTime end) {

        this.hour = hour;
        this.type = type;
        this.planet = planet;
        this.start = start;
        this.end = end;
    }

    public int getHour() {
        return hour;
    }

    public String getType() {
        return type;
    }

    public String getPlanet() {
        return planet;
    }

    public LocalTime getStart() {
        return start;
    }

    public LocalTime getEnd() {
        return end;
    }
}
