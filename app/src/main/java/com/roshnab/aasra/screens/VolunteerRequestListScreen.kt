package com.roshnab.aasra.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.roshnab.aasra.data.Report
import com.roshnab.aasra.data.ReportRepository
import kotlinx.coroutines.launch

@Composable
fun VolunteerRequestListScreen() {
    var reports by remember { mutableStateOf<List<Report>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        scope.launch {
            reports = ReportRepository.fetchOpenReports()
            isLoading = false
        }
    }

    Column(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        Text(
            text = "Active Requests",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )

        if (isLoading) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (reports.isEmpty()) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No active requests nearby.", color = Color.Gray)
            }
        } else {
            LazyColumn(contentPadding = PaddingValues(16.dp)) {
                items(reports) { report ->
                    RequestCard(report)
                }
            }
        }
    }
}

@Composable
fun RequestCard(report: Report) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp).clickable { /* Show Details */ },
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                val badgeColor = if (report.priority == "high") Color.Red else Color.Green
                Surface(color = badgeColor.copy(alpha = 0.1f), shape = RoundedCornerShape(4.dp)) {
                    Text(
                        text = report.category.uppercase(),
                        color = badgeColor,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
                Spacer(Modifier.weight(1f))
                Text("2.5 km", fontSize = 12.sp, color = Color.Gray)
            }

            Spacer(Modifier.height(8.dp))

            Text(report.description, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold)

            Spacer(Modifier.height(4.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Filled.LocationOn, null, modifier = Modifier.size(14.dp), tint = Color.Gray)
                Spacer(Modifier.width(4.dp))
                Text("Victim: ${report.victimName} (Age: ${report.victimAge})", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}