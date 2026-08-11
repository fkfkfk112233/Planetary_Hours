package com.example.Planetary_hours.calculator;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.Planetary_hours.model.Planet;

@Component
public class PlanetaryHourCalculator {

    // Chaldean order
    private static final List<Planet> PLANETS = List.of(
            Planet.SATURN,
            Planet.JUPITER,
            Planet.MARS,
            Planet.SUN,
            Planet.VENUS,
            Planet.MERCURY,
            Planet.MOON
    );

    /**
     * 計算完整 24 個 Planetary Hours
     */
    public List<String> calculate(
            LocalDate date,
            LocalTime sunrise,
            LocalTime sunset,
            LocalTime nextSunrise) {

        List<String> result = new ArrayList<>();

        // -------------------------
        // 1. 找出今天的 ruling planet
        // -------------------------

        Planet dayPlanet = getDayPlanet(date);

        // 找出它在 Chaldean order 的位置
        int planetIndex = PLANETS.indexOf(dayPlanet);

        // -------------------------
        // 2. 建立白天時間
        // -------------------------

        LocalDateTime dayStart =
                LocalDateTime.of(date, sunrise);

        LocalDateTime dayEnd =
                LocalDateTime.of(date, sunset);

        long daySeconds =
                Duration.between(dayStart, dayEnd).getSeconds();

        long dayHourSeconds =
                daySeconds / 12;

        LocalDateTime start = dayStart;

        // -------------------------
        // 3. 白天 12 個 Hours
        // -------------------------

        for (int i = 0; i < 12; i++) {

            LocalDateTime end =
                    start.plusSeconds(dayHourSeconds);

            Planet planet =
                    PLANETS.get(
                            (planetIndex + i) % PLANETS.size()
                    );

            result.add(
                    "Day Hour " + (i + 1)
                    + " : "
                    + planet
                    + " ("
                    + start.toLocalTime()
                    + " - "
                    + end.toLocalTime()
                    + ")"
            );

            start = end;
        }

        // -------------------------
        // 4. 夜晚
        // -------------------------

        LocalDateTime nightStart =
                LocalDateTime.of(date, sunset);

        LocalDateTime nightEnd =
                LocalDateTime.of(
                        date.plusDays(1),
                        nextSunrise
                );

        long nightSeconds =
                Duration.between(
                        nightStart,
                        nightEnd
                ).getSeconds();

        long nightHourSeconds =
                nightSeconds / 12;

        start = nightStart;

        // -------------------------
        // 5. 夜晚 12 個 Hours
        // -------------------------

        for (int i = 0; i < 12; i++) {

            LocalDateTime end =
                    start.plusSeconds(nightHourSeconds);

            Planet planet =
            		PLANETS.get(
                            (planetIndex + 12 + i)
                                    % PLANETS.size()
                    );

            result.add(
                    "Night Hour " + (i + 1)
                    + " : "
                    + planet
                    + " ("
                    + start.toLocalTime()
                    + " - "
                    + end.toLocalTime()
                    + ")"
            );

            start = end;
        }

        return result;
    }

    /**
     * 根據星期幾取得 ruling planet
     */
    private Planet getDayPlanet(LocalDate date) {

        return switch (date.getDayOfWeek()) {

            case SUNDAY ->
                    Planet.SUN;

            case MONDAY ->
                    Planet.MOON;

            case TUESDAY ->
                    Planet.MARS;

            case WEDNESDAY ->
                    Planet.MERCURY;

            case THURSDAY ->
                    Planet.JUPITER;

            case FRIDAY ->
                    Planet.VENUS;

            case SATURDAY ->
                    Planet.SATURN;
        };
    }
}
