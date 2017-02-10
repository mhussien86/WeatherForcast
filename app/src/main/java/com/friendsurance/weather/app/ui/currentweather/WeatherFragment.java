package com.friendsurance.weather.app.ui.currentweather;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.friendsurance.weather.app.R;
import com.friendsurance.weather.app.models.CurrentWeatherDataEnvelope;
import com.friendsurance.weather.app.models.WeatherDataEnvelope;
import com.friendsurance.weather.app.models.WeatherForecastListDataEnvelope;
import com.friendsurance.weather.app.ui.OnCitySelectedListner;
import com.friendsurance.weather.app.utils.Constants;
import com.friendsurance.weather.app.utils.DayFormatter;
import com.friendsurance.weather.app.utils.TemperatureFormatter;
import java.util.HashMap;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mohamed on 06/06/16.
 */
public class WeatherFragment extends Fragment implements WeatherView ,OnCitySelectedListner{


    WeatherPresenter weatherPresenter ;
    View rootView ;


    @Bind(R.id.city_name)
    TextView mCityName;

    @Bind(R.id.date)
    TextView mDate;

    @Bind(R.id.high_temp)
    TextView mHighTemperature;

    @Bind(R.id.low_temp)
    TextView mLowTemperature;

    @Bind(R.id.humidity)
    TextView mHumidity;

    @Bind(R.id.pressure)
    TextView mPressure;

    @Bind(R.id.weather_description)
    TextView mWeatherDescription;

    @Bind(R.id.weather_icon)
    ImageView mWeatherIcon;

    @Bind(R.id.weather_layout)
    RelativeLayout mWeatherLayout;


    @Bind(R.id.cardList)
    RecyclerView mWeatherList;

    ButterKnife binder;
    Toolbar toolbar;

    private String cityName = "" ;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        getActivity().invalidateOptionsMenu();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_weather,container,false);
        weatherPresenter = new WeatherPresenterImpl(this);
        binder.bind(this,rootView);
        mWeatherLayout.setVisibility(View.GONE);
        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        return rootView ;
    }

    ImageView refreshIcon = null ;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_refresh, menu);
        refreshIcon = (ImageView) menu.findItem(R.id.action_refresh).getActionView();
        refreshIcon.setImageResource(android.R.drawable.stat_notify_sync);

        refreshIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation rotation = AnimationUtils.loadAnimation(getActivity(), R.anim.clockwise_refresh);
                rotation.setRepeatCount(Animation.INFINITE);
                refreshIcon.startAnimation(rotation);
                weatherPresenter.getWeatherForCity(getCityName());
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

            return super.onOptionsItemSelected(item);
    }
    @Override
    public void onDestroyView() {
        weatherPresenter.onDestroy();
        binder.unbind(this);
        super.onDestroyView();
    }

    @Override
    public void showLoading() {

        View view = (View) rootView.findViewById(R.id.layout_loading);
        view.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        View view = (View) rootView.findViewById(R.id.layout_loading);
        view.setVisibility(view.GONE);

    }

    @Override
    public void showError() {
        clearAnimationForRefreshIcon();
        mWeatherLayout.setVisibility(View.GONE);
        addStubView();

    }

    public void clearAnimationForRefreshIcon(){
        if (refreshIcon!=null)refreshIcon.clearAnimation();
    }
    @Override
    public void onWeatherFetchedSuccessfully(HashMap<String, WeatherDataEnvelope> weatherData) {

        clearAnimationForRefreshIcon();
        hideLoading();

        mWeatherLayout.setVisibility(View.VISIBLE);
        removeStubView();
        CurrentWeatherDataEnvelope currentWeatherDataEnvelope = (CurrentWeatherDataEnvelope)weatherData.get(Constants.KEY_CURRENT_WEATHER);
        mCityName.setText(currentWeatherDataEnvelope.locationName);
        toolbar.setTitle(currentWeatherDataEnvelope.locationName);
        mDate.setText(new DayFormatter(getContext()).format(currentWeatherDataEnvelope.timestamp));
        mHighTemperature.setText(TemperatureFormatter.format(currentWeatherDataEnvelope.main.temp_max));
        mLowTemperature.setText(TemperatureFormatter.format(currentWeatherDataEnvelope.main.temp_min));
        mHumidity.setText(TemperatureFormatter.formatHumidty(currentWeatherDataEnvelope.main.humidity));
        mPressure.setText(TemperatureFormatter.formatPressure(currentWeatherDataEnvelope.main.pressure));
        mWeatherDescription.setText(currentWeatherDataEnvelope.weather.get(0).description);
        Glide.with(getContext()).load(Constants.WEATHER_IMAGE_URL+currentWeatherDataEnvelope.weather.get(0).icon+".png").into(mWeatherIcon);

        WeatherForecastListDataEnvelope weatherForecasts =(WeatherForecastListDataEnvelope) weatherData.get(Constants.KEY_WEATHER_FORECASTS);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mWeatherList.setLayoutManager(layoutManager);
        final WeatherForecastAdapter adapter = new WeatherForecastAdapter(getActivity(), weatherForecasts);
        mWeatherList.setHasFixedSize(true);

        // set elements to adapter
        mWeatherList.setAdapter(adapter);


    }



    void addStubView (){

        View stub = (View) rootView.findViewById(R.id.view_stub);
        stub.setVisibility(View.VISIBLE);

    }

    void removeStubView(){
        View stub = (View) rootView.findViewById(R.id.view_stub);
        stub.setVisibility(View.GONE);
    }

    @Override
    public String getCityName() {
        return cityName;
    }

    @Override
    public void onCityChanged(String cityName) {
        this.cityName = cityName ;
        weatherPresenter = new WeatherPresenterImpl(this);
        weatherPresenter.getWeatherForCity(cityName);

    }
}
