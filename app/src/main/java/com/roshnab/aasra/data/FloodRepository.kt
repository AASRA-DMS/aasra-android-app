package com.roshnab.aasra.data

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.osmdroid.util.GeoPoint
import java.net.URL

object FloodRepository {

    // The government URL containing the coordinates
    private const val DATA_URL = "https://ffd.pmd.gov.pk/js/kmz_values.js"

    suspend fun fetchBorderData(): List<GeoPoint> {
        return withContext(Dispatchers.IO) {
            val points = mutableListOf<GeoPoint>()
            try {
                // 1. Download the file content
                val jsContent = URL(DATA_URL).readText()

                // 2. Clean the string to get only the JSON array
                // The file format is: var kmz_pakistan=[[...],...];
                val startIndex = jsContent.indexOf("[[")
                val endIndex = jsContent.lastIndexOf("]]") + 2

                if (startIndex != -1 && endIndex != -1) {
                    val jsonString = jsContent.substring(startIndex, endIndex)
                    val jsonArray = JSONArray(jsonString)

                    for (i in 0 until jsonArray.length()) {
                        val point = jsonArray.getJSONArray(i)
                        // Data is [Longitude, Latitude] -> Convert to [Lat, Long]
                        val lon = point.getDouble(0)
                        val lat = point.getDouble(1)
                        points.add(GeoPoint(lat, lon))
                    }
                    Log.d("AASRA_DATA", "Fetched ${points.size} border points.")
                }
            } catch (e: Exception) {
                Log.e("AASRA_DATA", "Error fetching data: ${e.message}")
            }
            return@withContext points
        }
    }
}