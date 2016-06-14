package com.friendsurance.weather.app.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mohamed on 11/06/16.
 */
public class WeatherForecastListDataEnvelope extends WeatherDataEnvelope {


    public Location city;
    public List<ForecastDataEnvelope> list;

    public class Location {
        public String name;
    }

    public class ForecastDataEnvelope {
        @SerializedName("dt")
        public long timestamp;
        public float pressure ;
        public float humidity ;
        public Temperature temp;
        public List<Weather> weather;
    }

   public class Temperature {
        public float min;
        public float max;
    }

}
