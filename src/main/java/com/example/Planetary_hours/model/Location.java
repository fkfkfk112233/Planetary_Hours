package com.example.Planetary_hours.model;

public enum Location {

    TAIPEI(
            "Taipei",
            25.0330,
            121.5654
    ),

    NEW_TAIPEI(
            "New Taipei",
            25.0118,
            121.4628
    ),

    TAOYUAN(
            "Taoyuan",
            24.9937,
            121.3010
    ),

    TAICHUNG(
            "Taichung",
            24.1477,
            120.6736
    ),

    TAINAN(
            "Tainan",
            22.9997,
            120.2270
    ),

    KAOHSIUNG(
            "Kaohsiung",
            22.6273,
            120.3014
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
