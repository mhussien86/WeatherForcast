package com.friendsurance.weather.app.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.friendsurance.weather.app.ui.currentweather.WeatherFragment;
import com.friendsurance.weather.app.ui.listofcities.ListOfCitiesFragment;

/**
 * Created by mohamed on 08/02/17.
 */
public class WeatherPagerAdapter extends FragmentPagerAdapter {
    private static int NUM_ITEMS = 2;

    public WeatherPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    // Returns total number of pages
    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    // Returns the fragment to display for that page
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new ListOfCitiesFragment();
            case 1:
                return new WeatherFragment();
            default:
                return null;
        }
    }

    // Returns the page title for the top indicator
    @Override
    public CharSequence getPageTitle(int position) {
        return "Page " + position;
    }

}
