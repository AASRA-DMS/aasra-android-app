package com.roshnab.aasra.data

import org.osmdroid.util.GeoPoint

data class Barrage(
    val name: String,
    val river: String,
    val height: String,
    val location: GeoPoint,
    val inflow: String,
    val outflow: String,
    val status: String
)