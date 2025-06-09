package com.kodilla.proxy.db;

public class LazyWeatherForecastProxy implements WeatherForecast {

    private WeatherForecastImpl weatherForecast;

    @Override
    public String getWeather() {
        if (weatherForecast == null)
            return "Weather data not available (refresh not triggered)";
        return weatherForecast.getWeather();
    }

    @Override
    public void refreshWeather() throws InterruptedException {
        if (weatherForecast == null) {
            weatherForecast = new WeatherForecastImpl();
        } else {
            weatherForecast.refreshWeather();
        }
    }
}
