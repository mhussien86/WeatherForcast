package com.friendsurance.weather.app.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by mohamed on 11/06/16.
 */
public class CurrentWeatherDataEnvelope extends WeatherDataEnvelope{


    @SerializedName("name")
    public String locationName;
    @SerializedName("dt")
    public long timestamp;
    public List<Weather> weather;
    public Main main;

    public class Main {
        public float temp;
        public float temp_min;
        public float temp_max;
        public float pressure ;
        public float humidity ;

    }

}
