package com.example.Planetary_hours.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.Planetary_hours.calculator.PlanetaryHourCalculator;
import com.example.Planetary_hours.dto.SunData;

@Service
public class PlanetaryHourService {

    private final PlanetaryHourCalculator calculator;
    private final SunDataProvider sunDataProvider;

    public PlanetaryHourService(
            PlanetaryHourCalculator calculator,
            SunDataProvider sunDataProvider) {

        this.calculator = calculator;
        this.sunDataProvider = sunDataProvider;
    }

    public List<String> calculate(LocalDate date) {

        SunData sunData =
        		sunDataProvider.getSunData(date);

        LocalTime sunrise =
                sunData.getSunrise();

        LocalTime sunset =
                sunData.getSunset();

        LocalTime nextSunrise =
        		sunDataProvider
                        .getSunData(date.plusDays(1))
                        .getSunrise();

        return calculator.calculate(
                date,
                sunrise,
                sunset,
                nextSunrise
        );
    }
}
