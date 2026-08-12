package com.example.Planetary_hours.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Planetary_hours.dto.PlanetaryHourResponse;
import com.example.Planetary_hours.dto.PlanetaryHoursResponse;
import com.example.Planetary_hours.model.Location;
import com.example.Planetary_hours.service.PlanetaryHourService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(
	    name = "Planetary Hours",
	    description = "Planetary Hours calculation API"
	)
public class PlanetaryHourController {

    private final PlanetaryHourService planetaryHourService;

    public PlanetaryHourController(
            PlanetaryHourService planetaryHourService) {

        this.planetaryHourService = planetaryHourService;
    }

    @GetMapping("/api/planetary-hours")
    @Operation(
            summary = "Calculate planetary hours",
            description =
                "Calculate 24 planetary hours "
                + "for a specific date and location."
        )
    public PlanetaryHoursResponse calculate(
            @Parameter(
                    description = "Date to calculate",
                    example = "2026-08-13"
                )
            @RequestParam LocalDate date,
            
            @Parameter(
                    description = "Location"
                )
            @RequestParam Location location) {

        return planetaryHourService.calculate(date, location);
    }
}
