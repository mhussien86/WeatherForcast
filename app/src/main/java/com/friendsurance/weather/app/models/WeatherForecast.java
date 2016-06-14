package com.friendsurance.weather.app.models;

public class WeatherForecast  {

    private final String mLocationName;
    private final long mTimestamp;
    private final String mDescription;
    private final float mMinimumTemperature;
    private final float mMaximumTemperature;
    private final float humidity ;
    private final float pressure;

     public WeatherForecast(final String locationName,
                           final long timestamp,
                           final String description,
                           final float minimumTemperature,
                           final float maximumTemperature,
                            final float humidity,
                            final float pressure) {

        mLocationName = locationName;
        mTimestamp = timestamp;
        mMinimumTemperature = minimumTemperature;
        mMaximumTemperature = maximumTemperature;
        mDescription = description;
        this.humidity = humidity ;
        this.pressure = pressure ;
    }



    public String getLocationName() {
        return mLocationName;
    }

    public long getTimestamp() {
        return mTimestamp;
    }

    public String getDescription() {
        return mDescription;
    }

    public float getMinimumTemperature() {
        return mMinimumTemperature;
    }

    public float getMaximumTemperature() {
        return mMaximumTemperature;
    }

    public float getHumidity() {
        return humidity;
    }

    public float getPressure() {
        return pressure;
    }
}