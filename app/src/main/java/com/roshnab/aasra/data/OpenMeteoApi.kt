package com.roshnab.aasra.data

import retrofit2.http.GET
import retrofit2.http.Query

interface OpenMeteoApi {
    @GET("v1/flood")
    suspend fun getRiverDischarge(
        @Query("latitude") lat: Double,
        @Query("longitude") lng: Double,
        @Query("daily") daily: String = "river_discharge",
        @Query("forecast_days") forecastDays: Int = 3 // Get 3 days of forecast
    ): FloodResponse
}