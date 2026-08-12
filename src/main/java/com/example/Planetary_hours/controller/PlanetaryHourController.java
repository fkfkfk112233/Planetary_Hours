package com.example.Planetary_hours.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Planetary_hours.dto.PlanetaryHourResponse;
import com.example.Planetary_hours.model.Location;
import com.example.Planetary_hours.service.PlanetaryHourService;

@RestController
public class PlanetaryHourController {

    private final PlanetaryHourService planetaryHourService;

    public PlanetaryHourController(
            PlanetaryHourService planetaryHourService) {

        this.planetaryHourService = planetaryHourService;
    }

    @GetMapping("/api/planetary-hours")
    public List<PlanetaryHourResponse> calculate(
            @RequestParam LocalDate date,
            @RequestParam Location location) {

        return planetaryHourService.calculate(date, location);
    }
}
