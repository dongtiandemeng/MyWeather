package com.chen.chenbin.data;

import android.util.Log;

/**
 * Created by chenbin on 2016/6/17.
 */
public class SaveGetWeatherInstance {
    private static GetWeather getWeather;

    private static String GetWeatherJsonString;

    public  String getGetWeatherJsonString() {
        return GetWeatherJsonString;
    }

    public  void setGetWeatherJsonString(String getWeatherJsonString) {
        GetWeatherJsonString = getWeatherJsonString;
        Log.i("MyFragment","SaveGetWeatherInstance class GetWeatherJsonString"+GetWeatherJsonString);
    }

    public GetWeather getGetWeather() {
        return getWeather;
    }

    public void setGetWeather(GetWeather getWeather) {
        this.getWeather = getWeather;
    }
}
