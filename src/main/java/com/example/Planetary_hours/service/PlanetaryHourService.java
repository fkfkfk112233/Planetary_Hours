package com.example.Planetary_hours.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.Planetary_hours.calculator.PlanetaryHourCalculator;
import com.example.Planetary_hours.dto.PlanetaryHourResponse;
import com.example.Planetary_hours.dto.PlanetaryHoursResponse;
import com.example.Planetary_hours.dto.SunData;
import com.example.Planetary_hours.model.Location;

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

    public PlanetaryHoursResponse calculate(
            LocalDate date,
            Location location) {

        // 取得指定地點當天的日出、日落
        SunData sunData =
                sunDataProvider.getSunData(
                        date,
                        location
                );

        LocalTime sunrise =
                sunData.getSunrise();

        LocalTime sunset =
                sunData.getSunset();

        // 取得隔天日出
        LocalTime nextSunrise =
                sunDataProvider
                        .getSunData(
                                date.plusDays(1),
                                location
                        )
                        .getSunrise();

        // 計算 24 個 Planetary Hours
        List<PlanetaryHourResponse> hours =
                calculator.calculate(
                        date,
                        sunrise,
                        sunset,
                        nextSunrise
                );

        // 組合完整 Response
        return new PlanetaryHoursResponse(
                date,
                location.getName(),
                sunrise,
                sunset,
                hours
        );
    }
}
