package com.roshnab.aasra.data

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URL

object DonationRepository {

    private const val SHEET_URL = "https://docs.google.com/spreadsheets/d/e/2PACX-1vTJisIoOom2eVHs04PMBNhZW4YK-3GT3r3LdCtumq9PysfBe2417sAccYhc_vYEUoVZOg1YG8Mbo9-f/pub?gid=0&single=true&output=csv"

    suspend fun fetchDonations(): List<Donation> {
        return withContext(Dispatchers.IO) {
            val list = mutableListOf<Donation>()
            try {
                // 1. Download data
                val csvContent = URL(SHEET_URL).readText()

                // 2. Split into lines and skip the header row
                val rows = csvContent.lines().drop(1)

                for (row in rows) {
                    // Safety check: Skip empty lines
                    if (row.isBlank()) continue

                    // 3. Smart Split: Handle cases where Google adds quotes like "100,000"
                    // We simply remove all quotes and commas to be safe
                    val cleanRow = row.replace("\"", "")
                    val cols = cleanRow.split(",")

                    if (cols.size >= 4) {
                        val name = cols[0].trim()

                        // Fix for "100,000" -> "100000"
                        val amountString = cols[1].trim().replace(",", "")
                        val amount = amountString.toIntOrNull() ?: 0

                        val date = cols[2].trim()
                        val isAnonymous = cols[3].trim().equals("TRUE", ignoreCase = true)

                        list.add(Donation(name, amount, date, isAnonymous))
                    }
                }
            } catch (e: Exception) {
                Log.e("AASRA", "Error loading sheet", e)
                e.printStackTrace()
            }
            // Return sorted by highest donation
            return@withContext list.sortedByDescending { it.amount }
        }
    }
}