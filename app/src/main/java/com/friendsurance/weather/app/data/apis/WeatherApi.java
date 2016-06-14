package com.friendsurance.weather.app.data.apis;

import com.friendsurance.weather.app.data.ServiceGenerator;
import com.friendsurance.weather.app.models.CurrentWeatherDataEnvelope;
import com.friendsurance.weather.app.models.WeatherForecastListDataEnvelope;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by mohamed on 06/06/16.
 */
public interface WeatherApi {


    String API_KEY = "1630f10e1effeeedef083fc3fc0d2b95";

    @GET("weather?units=metric&appid=" + API_KEY)
    Observable<CurrentWeatherDataEnvelope> fetchCurrentWeather(@Query("q") String cityName);

    @GET("forecast/daily?units=metric&cnt=6&appid=" + API_KEY)
    Observable<WeatherForecastListDataEnvelope> fetchWeatherForecasts(@Query("q") String cityName);

}
