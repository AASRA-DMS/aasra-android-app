package com.roshnab.aasra.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.roshnab.aasra.data.Report
import com.roshnab.aasra.data.ReportRepository
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportScreen(onBackClick: () -> Unit) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var category by remember { mutableStateOf("Medical") }
    var description by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") } // NEW
    var isSubmitting by remember { mutableStateOf(false) }

    val currentLat = 31.5204
    val currentLng = 74.3587

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Report Disaster") },
                navigationIcon = { IconButton(onClick = onBackClick) { Icon(Icons.Filled.ArrowBack, "Back") } }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier.padding(padding).padding(16.dp).verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Please provide details so we can help you.", style = MaterialTheme.typography.bodyMedium)

            Text("Category", style = MaterialTheme.typography.titleSmall)
            Row {
                listOf("Medical", "Rescue", "Food", "Shelter").forEach { cat ->
                    FilterChip(
                        selected = category == cat,
                        onClick = { category = cat },
                        label = { Text(cat) },
                        modifier = Modifier.padding(end = 8.dp)
                    )
                }
            }

            OutlinedTextField(
                value = age,
                onValueChange = { age = it },
                label = { Text("Patient/Victim Age") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description (e.g. Need Insulin)") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            Button(
                onClick = {
                    if (description.isBlank() || age.isBlank()) {
                        Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                    isSubmitting = true
                    scope.launch {
                        val user = FirebaseAuth.getInstance().currentUser
                        val report = Report(
                            victimId = user?.uid ?: "",
                            victimName = user?.displayName ?: "Unknown",
                            victimPhone = user?.phoneNumber ?: "",
                            victimAge = age,
                            category = category,
                            description = description,
                            locationLat = currentLat,
                            locationLng = currentLng,
                            status = "pending"
                        )

                        val success = ReportRepository.submitReport(report)
                        isSubmitting = false
                        if (success) {
                            Toast.makeText(context, "Report Submitted!", Toast.LENGTH_LONG).show()
                            onBackClick()
                        } else {
                            Toast.makeText(context, "Failed to submit", Toast.LENGTH_SHORT).show()
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isSubmitting
            ) {
                Text(if (isSubmitting) "Sending..." else "Send SOS Request")
            }
        }
    }
}