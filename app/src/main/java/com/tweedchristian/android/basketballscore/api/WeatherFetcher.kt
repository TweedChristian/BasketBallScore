package com.tweedchristian.android.basketballscore.api

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

private const val TAG = "WeatherFetcher"

class WeatherFetcher {
    private val weatherAPI: WeatherAPI

    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        weatherAPI = retrofit.create(WeatherAPI::class.java)
    }

    fun fetchContents(): LiveData<WeatherData> {
        val responseLiveData: MutableLiveData<WeatherData> = MutableLiveData()
        val weatherRequest: Call<WeatherData> = weatherAPI.fetchHome()
        weatherRequest.enqueue(object: Callback<WeatherData> {
            override fun onFailure(call: Call<WeatherData>, t: Throwable) {
                Log.e(TAG, "Failed to Fetch Weather", t)
            }

            override fun onResponse(call: Call<WeatherData>, response: Response<WeatherData>) {
                Log.i(TAG, "Response Received")
                responseLiveData.value = response.body()
            }
        })
        return responseLiveData
    }
}