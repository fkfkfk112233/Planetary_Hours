package com.example.Planetary_hours.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Planetary_hours.service.PlanetaryHourService;

@RestController
public class PlanetaryHourController {

    private final PlanetaryHourService planetaryHourService;

    public PlanetaryHourController(
            PlanetaryHourService planetaryHourService) {

        this.planetaryHourService = planetaryHourService;
    }

    @GetMapping("/api/planetary-hours")
    public List<String> calculate(
            @RequestParam LocalDate date) {

        return planetaryHourService.calculate(date);
    }
}
