package org.hritik.weatherapp.Interface;

import org.hritik.weatherapp.Class.WeatherResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApi {
    @GET("current.json")
    Call<WeatherResponse> getWeatherByLocation(
            @Query("key") String apiKey,
            @Query("q") String location,
            @Query("aqi") String aqi
    );
}
