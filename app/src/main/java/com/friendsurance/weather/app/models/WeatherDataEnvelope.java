package com.friendsurance.weather.app.models;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;

/**
 * Created by mohamed on 11/06/16.
 */
public class WeatherDataEnvelope {

    @SerializedName("cod")
    private int httpCode;

    public class Weather {
        public String description;
        public String icon ;
    }

    /**
     * The web service always returns a HTTP header code of 200 and communicates errors
     * through a 'cod' field in the JSON payload of the response body.
     */
    public Observable filterWebServiceErrors() {
        if (httpCode == 200) {
            return Observable.just(this);
        } else {
            return Observable.error(new Exception());
        }
    }
}
