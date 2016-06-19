package com.friendsurance.weather.app.interactors;

import android.provider.SyncStateContract;
import android.util.Log;
import com.friendsurance.weather.app.data.ServiceGenerator;
import com.friendsurance.weather.app.data.apis.WeatherApi;
import com.friendsurance.weather.app.models.CurrentWeather;
import com.friendsurance.weather.app.models.CurrentWeatherDataEnvelope;
import com.friendsurance.weather.app.models.WeatherDataEnvelope;
import com.friendsurance.weather.app.models.WeatherForecast;
import com.friendsurance.weather.app.models.WeatherForecastListDataEnvelope;
import com.friendsurance.weather.app.utils.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeoutException;

import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by mohamed on 07/06/16.
 *
 * An interface implementation where all related usecases implemented
 *
 *
 */
public class WeatherInteractorImpl implements WeatherInteractor{



      final CompositeSubscription mCompositeSubscription;

    WeatherApi weatherService ;

    public WeatherInteractorImpl(){
        weatherService = new ServiceGenerator().createService(WeatherApi.class);
        mCompositeSubscription = new CompositeSubscription();



    }

    @Override
    public Observable<CurrentWeather> fetchCurrentWeather(String cityName) {


        return weatherService.fetchCurrentWeather(cityName)
                .flatMap(new Func1<CurrentWeatherDataEnvelope,
                        Observable<? extends CurrentWeatherDataEnvelope>>() {

                    // Error out if the request was not successful.
                    @Override
                    public Observable<? extends CurrentWeatherDataEnvelope> call(
                            final CurrentWeatherDataEnvelope data) {
                        return data.filterWebServiceErrors();
                    }

                }).map(new Func1<CurrentWeatherDataEnvelope, CurrentWeather>() {

                    // Parse the result and build a CurrentWeather object.
                    @Override
                    public CurrentWeather call(final CurrentWeatherDataEnvelope data) {

                        return new CurrentWeather(data.locationName, data.timestamp,
                                data.weather.get(0).description, data.main.temp,
                                data.main.temp_min, data.main.temp_max , data.main.humidity , data.main.pressure);


                    }
                });
    }




    @Override
    public Observable<List<WeatherForecast>> fetchWeatherForecasts(final String cityName) {
        return weatherService.fetchWeatherForecasts(cityName)
                .flatMap(new Func1<WeatherForecastListDataEnvelope,
                        Observable<? extends WeatherForecastListDataEnvelope>>() {

                    // Error out if the request was not successful.
                    @Override
                    public Observable<? extends WeatherForecastListDataEnvelope> call(
                            final WeatherForecastListDataEnvelope listData) {
                        return listData.filterWebServiceErrors();
                    }

                }).map(new Func1<WeatherForecastListDataEnvelope, List<WeatherForecast>>() {

                    // Parse the result and build a list of WeatherForecast objects.
                    @Override
                    public List<WeatherForecast> call(final WeatherForecastListDataEnvelope listData) {
                        final ArrayList<WeatherForecast> weatherForecasts =
                                new ArrayList<>();

                        for (WeatherForecastListDataEnvelope.ForecastDataEnvelope data : listData.list) {
                            final WeatherForecast weatherForecast = new WeatherForecast(
                                    listData.city.name, data.timestamp, data.weather.get(0).description,
                                    data.temp.min, data.temp.max,data.humidity,data.pressure);
                            weatherForecasts.add(weatherForecast);
                        }

                        return weatherForecasts;
                    }
                });

    }






    @Override
    public void loadAllData(String cityName, final OnRequestFinished onRequestFinished) {

        Observable fetchdata = Observable.zip( // Fetch current and 5 day forecasts for the city.
                weatherService.fetchCurrentWeather(cityName),
                weatherService.fetchWeatherForecasts(cityName),
                new Func2<CurrentWeatherDataEnvelope, WeatherForecastListDataEnvelope, HashMap<String, WeatherDataEnvelope>>() {
                    @Override
                    public HashMap call(final CurrentWeatherDataEnvelope currentWeather,
                                        final WeatherForecastListDataEnvelope weatherForecasts) {

                        HashMap weatherData = new HashMap();
                        weatherData.put(Constants.KEY_CURRENT_WEATHER, currentWeather);
                        weatherData.put(Constants.KEY_WEATHER_FORECASTS, weatherForecasts);
                        return weatherData;
                    }

                });




        mCompositeSubscription.add(fetchdata
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HashMap<String, WeatherDataEnvelope>>() {
                    @Override
                    public void onNext(final HashMap<String, WeatherDataEnvelope> weatherData) {

                        onRequestFinished.onDataFetchedSuccessful(weatherData);
                    }

                    @Override
                    public void onCompleted() {


                    }

                    @Override
                    public void onError(final Throwable error) {

                        onRequestFinished.onError();

                    }
                })
        );
    }

    @Override
    public void unsubscribeAll() {

        if(null !=mCompositeSubscription && mCompositeSubscription.hasSubscriptions()) {
            mCompositeSubscription.unsubscribe();
        }

    }
}
