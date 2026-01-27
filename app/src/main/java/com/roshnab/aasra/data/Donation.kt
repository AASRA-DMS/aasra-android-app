package com.roshnab.aasra.data

import androidx.compose.ui.graphics.Color

data class Donation(
    val name: String,
    val amount: Int,
    val date: String,
    val isAnonymous: Boolean,
    val email: String = ""
) {
    val tier: DonationTier
        get() = when {
            amount >= 50000 -> DonationTier.PLATINUM
            amount >= 20000 -> DonationTier.GOLD
            amount >= 5000 -> DonationTier.SILVER
            else -> DonationTier.BRONZE
        }

    val displayName: String
        get() {
            if (isAnonymous) {
                // Get the first name only (stop at the first space)
                val firstName = name.trim().substringBefore(" ")

                if (firstName.isNotEmpty()) {
                    // Keep first letter, replace rest with *
                    return firstName.first() + "*".repeat(firstName.length - 1)
                }
                return "Anonymous"
            }
            return name
        }
}

enum class DonationTier(val title: String, val color: Color, val icon: String) {
    PLATINUM("Savior", Color(0xFFE5E4E2), "👑"),
    GOLD("Guardian", Color(0xFFFFD700), "⭐"),
    SILVER("Defender", Color(0xFFC0C0C0), "🛡️"),
    BRONZE("Supporter", Color(0xFFCD7F32), "❤️")
}