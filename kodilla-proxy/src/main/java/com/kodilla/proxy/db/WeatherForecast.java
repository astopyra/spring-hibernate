package com.kodilla.proxy.db;

public interface WeatherForecast {

    String getWeather();
    void refreshWeather() throws InterruptedException;
}
