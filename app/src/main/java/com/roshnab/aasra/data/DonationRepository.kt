package com.roshnab.aasra.data

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URL

object DonationRepository {

    private const val SHEET_URL = "https://docs.google.com/spreadsheets/d/e/2PACX-1vTJisIoOom2eVHs04PMBNhZW4YK-3GT3r3LdCtumq9PysfBe2417sAccYhc_vYEUoVZOg1YG8Mbo9-f/pub?gid=0&single=true&output=csv"

    private var cachedDonations: List<Donation> = emptyList()

    suspend fun fetchDonations(forceRefresh: Boolean = false): List<Donation> {
        return withContext(Dispatchers.IO) {
            if (cachedDonations.isNotEmpty() && !forceRefresh) {
                Log.d("AASRA_DATA", "Returning Cached Data (Instant)")
                return@withContext cachedDonations
            }

            val list = mutableListOf<Donation>()
            try {
                Log.d("AASRA_DATA", "Downloading Fresh Data...")
                val csvContent = URL(SHEET_URL).readText()
                val rows = csvContent.lines().drop(1)

                for (row in rows) {
                    if (row.isBlank()) continue
                    val cleanRow = row.replace("\"", "")
                    val cols = cleanRow.split(",")

                    if (cols.size >= 4) {
                        val name = cols[0].trim()
                        val amountString = cols[1].trim().replace(",", "")
                        val amount = amountString.toIntOrNull() ?: 0
                        val date = cols[2].trim()
                        val isAnonymous = cols[3].trim().equals("TRUE", ignoreCase = true)

                        list.add(Donation(name, amount, date, isAnonymous))
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            val sortedList = list.sortedByDescending { it.amount }
            cachedDonations = sortedList
            return@withContext sortedList
        }
    }
}