package com.example.Planetary_hours.calculator;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.stereotype.Component;

@Component
public class SunCalculator {

    /**
     * 計算指定日期與地點的日出時間。
     */
    public LocalTime calculateSunrise(
            LocalDate date,
            double latitude,
            double longitude) {

        return calculate(date, latitude, longitude, true);
    }

    /**
     * 計算指定日期與地點的日落時間。
     */
    public LocalTime calculateSunset(
            LocalDate date,
            double latitude,
            double longitude) {

        return calculate(date, latitude, longitude, false);
    }

    /**
     * NOAA Solar Calculator 的簡化計算方法。
     */
    private LocalTime calculate(
            LocalDate date,
            double latitude,
            double longitude,
            boolean sunrise) {

        int dayOfYear = date.getDayOfYear();

        double lngHour = longitude / 15.0;

        double t;

        if (sunrise) {
            t = dayOfYear
                    + ((6 - lngHour) / 24.0);
        } else {
            t = dayOfYear
                    + ((18 - lngHour) / 24.0);
        }

        double meanAnomaly =
                (0.9856 * t) - 3.289;

        double sunLongitude =
                meanAnomaly
                + (1.916 * Math.sin(Math.toRadians(meanAnomaly)))
                + (0.020 * Math.sin(
                        Math.toRadians(2 * meanAnomaly)))
                + 282.634;

        sunLongitude =
                normalizeDegrees(sunLongitude);

        double rightAscension =
                Math.toDegrees(
                        Math.atan(
                                0.91764
                                * Math.tan(
                                        Math.toRadians(sunLongitude)
                                )
                        )
                );

        rightAscension =
                normalizeDegrees(rightAscension);

        double lQuadrant =
                Math.floor(sunLongitude / 90) * 90;

        double raQuadrant =
                Math.floor(rightAscension / 90) * 90;

        rightAscension +=
                lQuadrant - raQuadrant;

        rightAscension /= 15.0;

        double sinDeclination =
                0.39782
                * Math.sin(
                        Math.toRadians(sunLongitude)
                );

        double cosDeclination =
                Math.cos(
                        Math.asin(sinDeclination)
                );

        double zenith = 90.833;

        double cosHourAngle =
                (
                        Math.cos(Math.toRadians(zenith))
                        - (
                                sinDeclination
                                * Math.sin(
                                        Math.toRadians(latitude)
                                )
                        )
                )
                /
                (
                        cosDeclination
                        * Math.cos(
                                Math.toRadians(latitude)
                        )
                );

        // 太陽沒有升起 / 沒有落下
        if (cosHourAngle > 1 || cosHourAngle < -1) {
            throw new IllegalArgumentException(
                    "Sunrise or sunset does not exist "
                    + "for this location and date."
            );
        }

        double hourAngle;

        if (sunrise) {
            hourAngle =
                    360
                    - Math.toDegrees(
                            Math.acos(cosHourAngle)
                    );
        } else {
            hourAngle =
                    Math.toDegrees(
                            Math.acos(cosHourAngle)
                    );
        }

        hourAngle /= 15.0;

        double localMeanTime =
                hourAngle
                + rightAscension
                - (0.06571 * t)
                - 6.622;

        double utcTime =
                localMeanTime - lngHour;

        utcTime =
                normalizeHours(utcTime);

        // 台灣使用 UTC+8
        double taipeiTime =
                utcTime + 8;

        taipeiTime =
                normalizeHours(taipeiTime);

        return decimalHourToLocalTime(taipeiTime);
    }

    private double normalizeDegrees(double degrees) {

        degrees %= 360;

        if (degrees < 0) {
            degrees += 360;
        }

        return degrees;
    }

    private double normalizeHours(double hours) {

        hours %= 24;

        if (hours < 0) {
            hours += 24;
        }

        return hours;
    }

    private LocalTime decimalHourToLocalTime(
            double decimalHour) {

        int hour =
                (int) decimalHour;

        int minute =
                (int) ((decimalHour - hour) * 60);

        int second =
                (int) (
                        (
                                (
                                        decimalHour - hour
                                ) * 60
                                - minute
                        ) * 60
                );

        return LocalTime.of(
                hour,
                minute,
                second
        );
    }
}
