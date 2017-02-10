package com.friendsurance.weather.app.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.friendsurance.weather.app.R;
import com.friendsurance.weather.app.ui.currentweather.WeatherFragment;
import com.friendsurance.weather.app.ui.cusotmviews.CirclePageIndicator;

/**
 * Created by mohamed on 07/02/17.
 */
public class MainActivity extends AppCompatActivity implements OnCitySelectedListner{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        ViewPager vpPager = (ViewPager) findViewById(R.id.vpPager);
        WeatherPagerAdapter adapterViewPager = new WeatherPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);
        CirclePageIndicator mIndicator = (CirclePageIndicator)findViewById(R.id.indicator);
        mIndicator.setViewPager(vpPager);
    }

    @Override
    public void onCityChanged(String cityName) {
        FragmentManager fm = getSupportFragmentManager();
        WeatherFragment frag = (WeatherFragment) fm.getFragments().get(1);
        frag.onCityChanged(cityName);

    }
}
