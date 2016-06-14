package com.friendsurance.weather.app.models;

public class CurrentWeather extends WeatherForecast {
    private final float mTemperature;  // Current temperature.

    public CurrentWeather(final String locationName,
                          final long timestamp,
                          final String description,
                          final float temperature,
                          final float minimumTemperature,
                          final float maximumTemperature,
                          final float humidity,
                          final float pressure) {
        super(locationName, timestamp, description, minimumTemperature, maximumTemperature, humidity,pressure);
        mTemperature = temperature;
    }

    public float getTemperature() {
        return mTemperature;
    }
}
