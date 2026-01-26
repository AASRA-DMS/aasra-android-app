package com.roshnab.aasra.screens

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.roshnab.aasra.data.Donation
import com.roshnab.aasra.data.DonationRepository
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DonationScreen(onBackClick: () -> Unit) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var donations by remember { mutableStateOf<List<Donation>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var showBankDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        scope.launch {
            donations = DonationRepository.fetchDonations()
            isLoading = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Community Heroes", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { showBankDialog = true },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White,
                icon = { Icon(Icons.Filled.Favorite, null) },
                text = { Text("Donate Now", fontWeight = FontWeight.Bold) }
            )
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(bottom = 80.dp, top = 16.dp, start = 16.dp, end = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    item {
                        TotalDonationBanner(donations.sumOf { it.amount })
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    items(donations) { donation ->
                        DonorCard(donation)
                    }
                }
            }
        }
    }

    if (showBankDialog) {
        BankDetailsDialog(onDismiss = { showBankDialog = false })
    }
}

@Composable
fun TotalDonationBanner(total: Int) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Total Funds Raised", style = MaterialTheme.typography.labelLarge)
            Text(
                text = "PKR $total",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.primary
            )
            Text("Together we are AASRA.", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
        }
    }
}

@Composable
fun DonorCard(donation: Donation) {
    Card(
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 1. LEFT SIDE: Icon
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(donation.tier.color.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Text(donation.tier.icon, fontSize = 20.sp)
            }

            Spacer(modifier = Modifier.width(12.dp))

            // 2. MIDDLE: Name & Details (Takes remaining space)
            Column(
                modifier = Modifier.weight(1f) // This ensures the name doesn't push the amount off screen
            ) {
                Text(
                    text = donation.displayName,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = 1,               // Prevent wrapping
                    overflow = TextOverflow.Ellipsis // Add "..." if too long
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Tier Badge + Date Row
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        color = donation.tier.color.copy(alpha = 0.3f),
                        shape = RoundedCornerShape(4.dp),
                    ) {
                        Text(
                            text = donation.tier.title,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black.copy(alpha = 0.7f)
                        )
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(donation.date, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            // 3. RIGHT SIDE: Amount (Fixed width behavior)
            Text(
                text = "Rs. ${donation.amount}",
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 1 // Never wrap amount
            )
        }
    }
}

@Composable
fun BankDetailsDialog(onDismiss: () -> Unit) {
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(Icons.Filled.Favorite, null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(40.dp))
                Spacer(modifier = Modifier.height(16.dp))
                Text("Donate via Bank Transfer", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(16.dp))

                Column(
                    modifier = Modifier
                        .background(Color.Gray.copy(alpha = 0.1f), RoundedCornerShape(8.dp))
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    DetailRow("Bank Name", "Meezan Bank")
                    DetailRow("Account Title", "AASRA Foundation")
                    DetailRow("Account No", "01010101234567")

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                clipboardManager.setText(AnnotatedString("01010101234567"))
                                Toast.makeText(context, "Account Number Copied", Toast.LENGTH_SHORT).show()
                            },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End
                    ) {
                        Icon(Icons.Filled.ContentCopy, null, modifier = Modifier.size(16.dp), tint = MaterialTheme.colorScheme.primary)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Copy Account No", color = MaterialTheme.colorScheme.primary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
                Text("After payment, please share the screenshot via WhatsApp to update your name on the list.", textAlign = androidx.compose.ui.text.style.TextAlign.Center, fontSize = 12.sp, color = Color.Gray)
                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        val url = "https://wa.me/923001234567"
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        context.startActivity(intent)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF25D366)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Filled.Share, null, tint = Color.White)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Share Proof on WhatsApp", color = Color.White)
                }
            }
        }
    }
}

@Composable
fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, color = Color.Gray, fontSize = 14.sp)
        Text(value, fontWeight = FontWeight.Bold, fontSize = 14.sp)
    }
}