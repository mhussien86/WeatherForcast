package com.friendsurance.weather.app.ui.currentweather;

/**
 * Created by mohamed on 06/06/16.
 */
public interface WeatherPresenter {



    void getWeatherForCity(String cityName);

    void onDestroy();


}
