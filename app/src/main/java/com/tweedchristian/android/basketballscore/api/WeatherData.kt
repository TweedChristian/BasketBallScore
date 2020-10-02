package com.tweedchristian.android.basketballscore.api

import com.google.gson.annotations.SerializedName

 class WeatherData {
     var base: String = ""
     var visibility: Int = 0
     @SerializedName("coord") lateinit var coordinates: Coordinates
     @SerializedName("main") lateinit var temperature: Temperature
     @SerializedName("weather") lateinit var predictions: List<WeatherMain>
     @SerializedName("sys") lateinit var system: WeatherSystemData
     lateinit var wind: Wind
     lateinit var rain: Rain
     lateinit var clouds: Clouds
     var name: String = "Worcester"
     var id: Int = 100
     var timeZone: Int = 1000
     var cod: Int = 200
     var dt: Long = 1000000
 }



data class Coordinates (
    @SerializedName("lat") var latitude: Double = 0.0,
    @SerializedName("lon") var longitude: Double = 0.0
)

data class Temperature (
    @SerializedName("temp") var temperature: Double = 39.0,
    @SerializedName("feels_like") var feelsLike: Double = 39.0,
    @SerializedName("temp_ min") var temperatureMinimum: Double = 39.0,
    @SerializedName("temp_max") var temperatureMaximum: Double = 39.0,
    var pressure: Int = 100,
    var humidity: Int = 100,
)

data class Wind (
    var speed: Double = 0.0,
    @SerializedName("deg") var degree: Int = 10
)

data class Rain (
    @SerializedName("1h") var pH: Double = 0.5
)

data class Clouds (
    @SerializedName("all") var percent: Int = 50
)

data class WeatherMain (
    var id: Int = 100,
    @SerializedName("main") var mainPrediction: String = "sunny",
    var description: String = "no clouds",
    var icon: String = "10n"
)

data class WeatherSystemData (
    var type: Int = 1,
    var id: Int = 100,
    var country: String = "US",
    var sunrise: Long = 10000000,
    var sunset: Long = 100000
)