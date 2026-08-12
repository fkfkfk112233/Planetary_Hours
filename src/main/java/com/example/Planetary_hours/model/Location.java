package com.example.Planetary_hours.model;

public enum Location {

    TAIPEI(
            "Taipei",
            25.0330,
            121.5654
    );
	
	private final String name;
	//緯度
	private final double latitude;
	//經度
	private final double longitude;

    Location(
            String name,
            double latitude,
            double longitude) {

        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
