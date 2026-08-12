package com.example.Planetary_hours.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class PlanetaryHoursResponse {

    private final LocalDate date;

    private final String location;

    private final LocalTime sunrise;

    private final LocalTime sunset;

    private final List<PlanetaryHourResponse> hours;

    public PlanetaryHoursResponse(
            LocalDate date,
            String location,
            LocalTime sunrise,
            LocalTime sunset,
            List<PlanetaryHourResponse> hours) {

        this.date = date;
        this.location = location;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.hours = hours;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getLocation() {
        return location;
    }

    public LocalTime getSunrise() {
        return sunrise;
    }

    public LocalTime getSunset() {
        return sunset;
    }

    public List<PlanetaryHourResponse> getHours() {
        return hours;
    }
}
