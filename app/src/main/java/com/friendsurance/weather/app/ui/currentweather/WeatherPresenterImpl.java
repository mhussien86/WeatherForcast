package com.friendsurance.weather.app.ui.currentweather;

import com.friendsurance.weather.app.interactors.WeatherInteractor;
import com.friendsurance.weather.app.interactors.WeatherInteractorImpl;
import com.friendsurance.weather.app.models.WeatherDataEnvelope;

import java.util.HashMap;

/**
 * Created by mohamed on 06/06/16.
 *
 *
 *
 */
public class WeatherPresenterImpl implements WeatherPresenter , WeatherInteractor.OnRequestFinished {


    WeatherView weatherView ;
    WeatherInteractor weatherInteractor ;

    public WeatherPresenterImpl(WeatherView weatherView){

        weatherInteractor = new WeatherInteractorImpl();
        this.weatherView = weatherView ;

    }
    @Override
    public void getWeatherForCity(String cityName) {

        weatherInteractor.loadAllData(cityName,this);
    }

    @Override
    public void onDestroy() {

        weatherInteractor.unsubscribeAll();
        weatherView = null ;

    }

    @Override
    public void onDataFetchedSuccessful(HashMap<String, WeatherDataEnvelope> weatherData) {


        weatherView.onWeatherFetchedSuccessfully(weatherData);

    }

    @Override
    public void onError() {

        weatherView.hideLoading();
        weatherView.showError();
    }
}
