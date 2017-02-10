package com.friendsurance.weather.app.models;

/**
 * Created by mohamed on 09/02/17.
 */
public class City {


    String cityName ;
    Boolean isSelected ;

    public City(String cityName, Boolean isSelected){
        this.cityName = cityName ;
        this.isSelected = isSelected;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Boolean isSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }


}
