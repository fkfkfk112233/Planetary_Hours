package com.example.Planetary_hours.dto;

import java.time.LocalTime;

public class SunData {

    private final LocalTime sunrise;
    private final LocalTime sunset;

    public SunData(
            LocalTime sunrise,
            LocalTime sunset) {

        this.sunrise = sunrise;
        this.sunset = sunset;
    }

    public LocalTime getSunrise() {
        return sunrise;
    }

    public LocalTime getSunset() {
        return sunset;
    }
}
