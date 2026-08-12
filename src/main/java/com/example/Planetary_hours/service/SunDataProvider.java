package com.example.Planetary_hours.service;

import java.time.LocalDate;

import com.example.Planetary_hours.dto.SunData;

public interface SunDataProvider {

	SunData getSunData(LocalDate date);
}
