package com.roshnab.aasra.screens

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class AuthViewModel(application: Application) : AndroidViewModel(application) {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()
    private val context = application.applicationContext

    fun signUp(
        email: String,
        pass: String,
        name: String,
        phone: String,
        role: String,
        skills: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val authResult = auth.createUserWithEmailAndPassword(email, pass).await()
                val user = authResult.user

                if (user != null) {
                    val userData = hashMapOf(
                        "uid" to user.uid,
                        "name" to name,
                        "email" to email,
                        "phone" to phone,
                        "role" to role,
                        "skills" to skills,
                        "createdAt" to System.currentTimeMillis()
                    )


                    db.collection("users").document(user.uid).set(userData).await()

                    Toast.makeText(context, "Welcome, $name!", Toast.LENGTH_SHORT).show()
                    onSuccess()
                }
            } catch (e: Exception) {
                onError(e.message ?: "Signup Failed")
            }
        }
    }
}