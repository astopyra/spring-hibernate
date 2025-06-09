package com.kodilla.proxy;

import com.kodilla.proxy.db.*;

import java.util.Random;

public class WeatherApp {

    public static void main(String[] args) throws InterruptedException {
        long begin = System.currentTimeMillis();
        Random random = new Random();

        for (int n = 0; n < 5; n++) {
            WeatherForecast forecast = new LazyWeatherForecastProxy();
            System.out.println(forecast.getWeather());
            if (random.nextDouble() < 0.2) {
                forecast.refreshWeather();
                System.out.println("After refresh: " + forecast.getWeather());
            }
        }

        long end = System.currentTimeMillis();
        System.out.println("The execution took: " + (end - begin) + "[ms]");
    }
}
