package com.example.Planetary_hours.service;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.stereotype.Service;

import com.example.Planetary_hours.dto.SunData;

@Service
public class SunDataService {

    public SunData getSunData(LocalDate date) {

        // 暫時使用測試資料
        LocalTime sunrise = LocalTime.of(5, 30);
        LocalTime sunset = LocalTime.of(18, 30);

        return new SunData(
                sunrise,
                sunset
        );
    }
}
