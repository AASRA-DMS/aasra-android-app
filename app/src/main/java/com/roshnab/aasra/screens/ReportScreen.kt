package com.roshnab.aasra.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.PhotoCamera
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportScreen(
    latitude: Double,
    longitude: Double,
    onBackClick: () -> Unit,
    onSubmitClick: () -> Unit
) {
    // Form State
    var description by remember { mutableStateOf("") }
    var affectedCount by remember { mutableStateOf(1) }
    var selectedType by remember { mutableStateOf("Flood") }
    var contactNumber by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Report Incident", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            // 1. LOCATION CARD
            LocationCard(latitude, longitude)

            // 2. INCIDENT TYPE
            Text("What is happening?", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IncidentTypeItem("Flood", Icons.Filled.WaterDrop, selectedType == "Flood") { selectedType = "Flood" }
                IncidentTypeItem("Fire", Icons.Filled.LocalFireDepartment, selectedType == "Fire") { selectedType = "Fire" }
                IncidentTypeItem("Medical", Icons.Filled.MedicalServices, selectedType == "Medical") { selectedType = "Medical" }
                IncidentTypeItem("Other", Icons.Filled.Warning, selectedType == "Other") { selectedType = "Other" }
            }

            // 3. PEOPLE AFFECTED
            Text("People Affected?", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            PeopleCounter(count = affectedCount, onCountChange = { affectedCount = it })

            // 4. ADD PHOTO
            OutlinedButton(
                onClick = { /* TODO: Open Camera */ },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Outlined.PhotoCamera, null)
                Spacer(Modifier.width(8.dp))
                Text("Add Photo / Video Evidence")
            }

            // 5. DESCRIPTION
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Describe the situation (Optional)") },
                modifier = Modifier.fillMaxWidth().height(120.dp),
                shape = RoundedCornerShape(12.dp)
            )

            // 6. CONTACT
            OutlinedTextField(
                value = contactNumber,
                onValueChange = { contactNumber = it },
                label = { Text("Your Phone Number") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                leadingIcon = { Icon(Icons.Filled.Phone, null) }
            )

            Spacer(Modifier.height(16.dp))

            // 7. SUBMIT BUTTON
            Button(
                onClick = onSubmitClick,
                modifier = Modifier.fillMaxWidth().height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("SUBMIT REPORT", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
        }
    }
}

// --- HELPER COMPONENTS ---

@Composable
fun LocationCard(lat: Double, lng: Double) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Filled.Place, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
            Spacer(Modifier.width(16.dp))
            Column {
                Text("Location Detected", style = MaterialTheme.typography.labelMedium, color = Color.Gray)
                Text("${lat.toString().take(7)}, ${lng.toString().take(7)}", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun IncidentTypeItem(name: String, icon: ImageVector, isSelected: Boolean, onClick: () -> Unit) {
    val backgroundColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant
    val iconColor = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .background(backgroundColor),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = name, tint = iconColor, modifier = Modifier.size(28.dp))
        }
        Spacer(Modifier.height(8.dp))
        Text(name, fontSize = 12.sp, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal)
    }
}

@Composable
fun PeopleCounter(count: Int, onCountChange: (Int) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.Gray.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { if (count > 1) onCountChange(count - 1) }) {
            Icon(Icons.Filled.Remove, null)
        }
        Text("$count Person(s)", fontWeight = FontWeight.Bold, fontSize = 18.sp)
        IconButton(onClick = { onCountChange(count + 1) }) {
            Icon(Icons.Filled.Add, null)
        }
    }
}