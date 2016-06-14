package com.friendsurance.weather.app.utils;

public class TemperatureFormatter {


    // Helper class to format temperature
    public static String format(float temperature) {
        return String.valueOf(Math.round(temperature)) + "°";
    }

    // Helper class to format temperature
    public static String formatHumidty(float temperature) {
        return String.valueOf(Math.round(temperature)) + "%";
    }

    // Helper class to format temperature
    public static String formatPressure(float temperature) {
        return String.valueOf(Math.round(temperature)) + " hpa";
    }
}
