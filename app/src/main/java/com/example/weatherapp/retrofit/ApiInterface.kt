package com.example.weatherapp.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("data/2.5/weather?&units=metric&APPID=04a42b96398abc8e4183798ed22f9485")
    fun weatherApi(
        @Query("q") cityName: String
    ): Call<WeatherResponse>
}