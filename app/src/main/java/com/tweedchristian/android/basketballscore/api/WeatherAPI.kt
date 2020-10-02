package com.tweedchristian.android.basketballscore.api

import retrofit2.Call
import retrofit2.http.GET

interface WeatherAPI {
    @GET("data/2.5/weather?q=London,uk&APPID=8fb4c58540cf01a9a99402a2ff414162")
    fun fetchHome(): Call<WeatherData>
}