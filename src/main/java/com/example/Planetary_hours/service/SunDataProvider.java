package com.example.Planetary_hours.service;

import java.time.LocalDate;

import com.example.Planetary_hours.dto.SunData;
import com.example.Planetary_hours.model.Location;

public interface SunDataProvider {

	SunData getSunData(LocalDate date, Location location);
}
