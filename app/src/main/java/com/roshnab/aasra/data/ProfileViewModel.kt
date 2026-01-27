package com.roshnab.aasra.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.roshnab.aasra.data.DonationRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ProfileViewModel : ViewModel() {

    var uiState by mutableStateOf(ProfileUiState())
        private set

    init {
        loadProfileData()
    }

    private fun loadProfileData() {
        viewModelScope.launch {
            val user = FirebaseAuth.getInstance().currentUser

            if (user != null) {
                var name = user.displayName ?: "AASRA User"
                val email = user.email ?: ""

                try {
                    val snapshot = FirebaseFirestore.getInstance()
                        .collection("users")
                        .document(user.uid)
                        .get()
                        .await()

                    if (snapshot.exists()) {
                        val firestoreName = snapshot.getString("name")
                        if (!firestoreName.isNullOrBlank()) name = firestoreName
                    }
                } catch (e: Exception) {
                }

                val allDonations = DonationRepository.fetchDonations()

                val userTotal = allDonations
                    .filter { it.name.equals(name, ignoreCase = true) }
                    .sumOf { it.amount }

                uiState = uiState.copy(
                    isLoading = false,
                    name = name,
                    email = email,
                    totalDonated = userTotal
                )
            } else {
                uiState = uiState.copy(isLoading = false)
            }
        }
    }
}

data class ProfileUiState(
    val isLoading: Boolean = true,
    val name: String = "",
    val email: String = "",
    val totalDonated: Int = 0
)