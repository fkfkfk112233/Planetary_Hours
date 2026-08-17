package com.example.Planetary_hours.dto;

import com.example.Planetary_hours.model.Planet;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ActivityRequest {

    @NotNull(message = "Planet 不可為空")
    private Planet planet;

    @NotBlank(message = "Activity 名稱不可為空")
    private String name;

    private String description;


    public Planet getPlanet() {
        return planet;
    }


    public void setPlanet(Planet planet) {
        this.planet = planet;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }
}