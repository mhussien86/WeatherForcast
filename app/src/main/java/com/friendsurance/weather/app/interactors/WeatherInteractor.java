package com.friendsurance.weather.app.interactors;

import com.friendsurance.weather.app.models.CurrentWeather;
import com.friendsurance.weather.app.models.WeatherDataEnvelope;
import com.friendsurance.weather.app.models.WeatherForecast;

import java.util.HashMap;
import java.util.List;

import rx.Observable;

/**
 * Created by mohamed on 07/06/16.
 */
public interface WeatherInteractor {


    interface OnRequestFinished {

        void onDataFetchedSuccessful(HashMap<String, WeatherDataEnvelope> weatherData);

        void onError();

    }
    Observable<CurrentWeather> fetchCurrentWeather(final String cityName);

    Observable<List<WeatherForecast>> fetchWeatherForecasts(final String cityName);

    void loadAllData(String cityName, OnRequestFinished onRequestFinished);
    void unsubscribeAll();
    


}
