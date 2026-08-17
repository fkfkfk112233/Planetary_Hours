package com.example.Planetary_hours.dto;

import com.example.Planetary_hours.model.Activity;
import com.example.Planetary_hours.model.Planet;

public class ActivityResponse {

    private final Long id;

    private final Planet planet;

    private final String name;

    private final String description;


    public ActivityResponse(
            Long id,
            Planet planet,
            String name,
            String description) {

        this.id = id;
        this.planet = planet;
        this.name = name;
        this.description = description;
    }


    // ========================================
    // Entity → DTO
    // ========================================

    public static ActivityResponse from(
            Activity activity) {

        return new ActivityResponse(
                activity.getId(),
                activity.getPlanet(),
                activity.getName(),
                activity.getDescription()
        );
    }


    public Long getId() {
        return id;
    }


    public Planet getPlanet() {
        return planet;
    }


    public String getName() {
        return name;
    }


    public String getDescription() {
        return description;
    }
}
