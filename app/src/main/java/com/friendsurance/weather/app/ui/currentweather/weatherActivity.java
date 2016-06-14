package com.friendsurance.weather.app.ui.currentweather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.friendsurance.weather.app.R;

public class weatherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new WeatherFragment())
                    .commit();
        }


    }

}
