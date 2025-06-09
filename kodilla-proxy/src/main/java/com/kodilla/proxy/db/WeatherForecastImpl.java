package com.kodilla.proxy.db;

public class WeatherForecastImpl implements WeatherForecast {

    private String weatherDescription;

    public WeatherForecastImpl () throws InterruptedException {
        refreshWeather();
    }

    @Override
    public String getWeather() {
        return weatherDescription;
    }

    @Override
    public void refreshWeather() throws InterruptedException {
        System.out.println("Refreshing weather data...");
        Thread.sleep(5000);
        this.weatherDescription = "It's beautifull sunny day, 25°C.";
    }
}
