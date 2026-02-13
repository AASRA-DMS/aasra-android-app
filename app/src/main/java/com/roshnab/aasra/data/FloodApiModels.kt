package com.roshnab.aasra.data

import com.google.gson.annotations.SerializedName

data class FloodResponse(
    val latitude: Double,
    val longitude: Double,
    val daily: DailyFloodData
)

data class DailyFloodData(
    val time: List<String>,
    @SerializedName("river_discharge")
    val riverDischarge: List<Double>
)