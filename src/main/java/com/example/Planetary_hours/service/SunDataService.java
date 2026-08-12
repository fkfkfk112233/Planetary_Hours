package com.example.Planetary_hours.service;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.stereotype.Service;

import com.example.Planetary_hours.calculator.SunCalculator;
import com.example.Planetary_hours.dto.SunData;
import com.example.Planetary_hours.model.Location;

@Service
public class SunDataService implements SunDataProvider{

    private final SunCalculator sunCalculator;

    public SunDataService(
            SunCalculator sunCalculator) {

        this.sunCalculator = sunCalculator;
    }

    @Override
    public SunData getSunData(
            LocalDate date,
            Location location) {

        LocalTime sunrise =
                sunCalculator.calculateSunrise(
                        date,
                        location.getLatitude(),
                        location.getLongitude()
                );

        LocalTime sunset =
                sunCalculator.calculateSunset(
                        date,
                        location.getLatitude(),
                        location.getLongitude()
                );

        return new SunData(
                sunrise,
                sunset
        );
    }
}
