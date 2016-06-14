package com.friendsurance.weather.app.ui.currentweather;

import com.friendsurance.weather.app.models.WeatherDataEnvelope;

import java.util.HashMap;

/**
 * Created by mohamed on 06/06/16.
 */
public interface WeatherView {


    void showLoading();
    void hideLoading();

    void showError();
    void onWeatherFetchedSuccessfully(HashMap<String, WeatherDataEnvelope> weatherData);

    String getCityName();


}
