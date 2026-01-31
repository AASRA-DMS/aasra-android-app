package com.roshnab.aasra.screens

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.BitmapDrawable
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.roshnab.aasra.components.AasraBottomBar
import com.roshnab.aasra.components.AasraTopBar
import com.roshnab.aasra.components.BottomNavScreen
import com.roshnab.aasra.data.*
import kotlinx.coroutines.launch
import org.osmdroid.api.IMapController
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Polygon
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay

@Composable
fun VolunteerHomeScreen(onLogoutClick: () -> Unit) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var currentScreen by remember { mutableStateOf(BottomNavScreen.Home) }

    // --- Map Data States ---
    var riverPolygons by remember { mutableStateOf<List<List<GeoPoint>>>(emptyList()) }
    var riverBarrages by remember { mutableStateOf<List<Barrage>>(emptyList()) }
    var riverBasin by remember { mutableStateOf<List<GeoPoint>>(emptyList()) }

    // --- Live Data from Firebase ---
    val activeReports by ReportRepository.getOpenReportsFlow().collectAsState(initial = emptyList())

    var selectedReport by remember { mutableStateOf<Report?>(null) }
    var mapController by remember { mutableStateOf<IMapController?>(null) }

    // Load Static River Data
    LaunchedEffect(Unit) {
        scope.launch {
            riverPolygons = RiverRepository.getRiverPolygons()
            riverBarrages = RiverRepository.getBarrages()
            riverBasin = RiverRepository.getRiverBasin()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        Box(modifier = Modifier.fillMaxSize()) {
            when (currentScreen) {
                BottomNavScreen.Home -> {
                    Scaffold(
                        topBar = {
                            AasraTopBar(
                                onProfileClick = { currentScreen = BottomNavScreen.Profile },
                                onNotificationClick = {}
                            )
                        }
                    ) { padding ->
                        Box(modifier = Modifier.padding(padding).fillMaxSize()) {

                            // --- THE MAP ---
                            AndroidView(
                                modifier = Modifier.fillMaxSize(),
                                factory = { ctx ->
                                    MapView(ctx).apply {
                                        setTileSource(TileSourceFactory.MAPNIK)
                                        setMultiTouchControls(true)
                                        setLayerType(android.view.View.LAYER_TYPE_HARDWARE, null)

                                        mapController = this.controller
                                        mapController?.setZoom(6.0)
                                        mapController?.setCenter(GeoPoint(30.0, 70.0))
                                    }
                                },
                                update = { map ->
                                    // 1. My Location
                                    if (map.overlays.none { it is MyLocationNewOverlay }) {
                                        val locOverlay = MyLocationNewOverlay(GpsMyLocationProvider(context), map)
                                        locOverlay.enableMyLocation()
                                        map.overlays.add(locOverlay)
                                    }

                                    // 2. River Basin & Rivers (Blue)
                                    if (riverBasin.isNotEmpty()) {
                                        map.overlays.removeAll { it is Polygon && it.title == "River Basin" }
                                        val basinShape = Polygon().apply {
                                            points = riverBasin
                                            fillPaint.color = android.graphics.Color.argb(40, 135, 206, 235)
                                            outlinePaint.color = android.graphics.Color.TRANSPARENT
                                            title = "River Basin"
                                        }
                                        map.overlays.add(1, basinShape)
                                    }

                                    if (riverPolygons.isNotEmpty()) {
                                        map.overlays.removeAll { it is Polygon && it.title == "River Water" }
                                        riverPolygons.forEach { riverPoints ->
                                            val riverShape = Polygon().apply {
                                                points = riverPoints
                                                fillPaint.color = android.graphics.Color.argb(150, 30, 144, 255)
                                                outlinePaint.color = android.graphics.Color.parseColor("#1E90FF")
                                                outlinePaint.strokeWidth = 3f
                                                title = "River Water"
                                            }
                                            map.overlays.add(riverShape)
                                        }
                                    }

                                    // 3. Barrages (Green Dots - Non-clickable in volunteer view to reduce clutter, or clickable if you prefer)
                                    if (riverBarrages.isNotEmpty()) {
                                        map.overlays.removeAll { it is Marker && it.title?.startsWith("Barrage") == true }

                                        val bSize = 24
                                        val bBitmap = Bitmap.createBitmap(bSize, bSize, Bitmap.Config.ARGB_8888)
                                        val bCanvas = Canvas(bBitmap)
                                        val bPaint = Paint().apply { color = android.graphics.Color.parseColor("#008000"); style = Paint.Style.FILL }
                                        bCanvas.drawCircle(bSize/2f, bSize/2f, bSize/2f, bPaint)
                                        val bIcon = BitmapDrawable(context.resources, bBitmap)

                                        riverBarrages.forEach { barrage ->
                                            val m = Marker(map).apply {
                                                position = barrage.location
                                                icon = bIcon
                                                title = "Barrage: ${barrage.name}"
                                                setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER)
                                            }
                                            map.overlays.add(m)
                                        }
                                    }

                                    // 4. FIREBASE REPORTS (RED MARKERS)
                                    if (activeReports.isNotEmpty()) {
                                        map.overlays.removeAll { it is Marker && it.title?.startsWith("SOS") == true }

                                        // Custom Red Icon
                                        val rSize = 48
                                        val rBitmap = Bitmap.createBitmap(rSize, rSize, Bitmap.Config.ARGB_8888)
                                        val rCanvas = Canvas(rBitmap)
                                        val rPaint = Paint()
                                        rPaint.isAntiAlias = true

                                        // Red Circle
                                        rPaint.color = android.graphics.Color.RED
                                        rPaint.style = Paint.Style.FILL
                                        rCanvas.drawCircle(rSize / 2f, rSize / 2f, rSize / 2f, rPaint)

                                        // White '!'
                                        rPaint.color = android.graphics.Color.WHITE
                                        rPaint.strokeWidth = 6f
                                        rCanvas.drawLine(rSize/2f, rSize/4f, rSize/2f, rSize/1.5f, rPaint)
                                        rCanvas.drawCircle(rSize/2f, rSize/1.25f, 3f, rPaint)

                                        val reportIcon = BitmapDrawable(context.resources, rBitmap)

                                        activeReports.forEach { report ->
                                            if (report.locationLat != 0.0 && report.locationLng != 0.0) {
                                                val marker = Marker(map).apply {
                                                    position = GeoPoint(report.locationLat, report.locationLng)
                                                    icon = reportIcon
                                                    title = "SOS: ${report.category}"
                                                    setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER)
                                                    setOnMarkerClickListener { _, _ ->
                                                        selectedReport = report
                                                        true
                                                    }
                                                }
                                                map.overlays.add(marker)
                                            }
                                        }
                                    }
                                    map.invalidate()
                                }
                            )

                            // Volunteer Badge Overlay
                            Box(Modifier.align(Alignment.TopStart).padding(16.dp)) {
                                AssistChip(
                                    onClick = {},
                                    label = { Text("Active Volunteer Mode") },
                                    leadingIcon = { Icon(Icons.Default.Warning, null) },
                                    colors = AssistChipDefaults.assistChipColors(
                                        containerColor = MaterialTheme.colorScheme.errorContainer,
                                        labelColor = MaterialTheme.colorScheme.onErrorContainer
                                    )
                                )
                            }
                        }
                    }

                    // Show Report Details Dialog
                    if (selectedReport != null) {
                        FirebaseReportDialog(report = selectedReport!!, onDismiss = { selectedReport = null })
                    }
                }
                BottomNavScreen.Requests -> {
                    VolunteerRequestListScreen()
                }
                BottomNavScreen.Safety -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Safety Guidelines & Protocols")
                    }
                }
                BottomNavScreen.Profile -> {
                    Box(modifier = Modifier.padding(bottom = 100.dp)) {
                        val profileViewModel: ProfileViewModel = viewModel()
                        ProfileScreen(
                            onBackClick = { currentScreen = BottomNavScreen.Home },
                            onLogoutClick = onLogoutClick,
                            onAddLocationClick = {},
                            onEditProfileClick = {},
                            isDarkTheme = false,
                            onThemeChanged = {},
                            onSupportClick = {},
                            viewModel = profileViewModel
                        )
                    }
                }
                else -> {}
            }
        }

        Column(
            modifier = Modifier.align(Alignment.BottomCenter).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AasraBottomBar(
                currentScreen = currentScreen,
                items = listOf(
                    BottomNavScreen.Home,
                    BottomNavScreen.Requests,
                    BottomNavScreen.Safety,
                    BottomNavScreen.Profile
                ),
                onScreenSelected = { screen -> currentScreen = screen }
            )
        }
    }
}

@Composable
fun FirebaseReportDialog(report: Report, onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(Modifier.padding(16.dp)) {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "SOS: ${report.category}",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color.Red
                    )
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, "Close")
                    }
                }
                HorizontalDivider()
                Spacer(Modifier.height(8.dp))

                Text("Victim: ${report.victimName}", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Text("Phone: ${report.victimPhone}", style = MaterialTheme.typography.bodyMedium)
                Spacer(Modifier.height(4.dp))

                Text("Description:", style = MaterialTheme.typography.labelLarge, color = Color.Gray)
                Text(report.description, style = MaterialTheme.typography.bodyLarge)

                Spacer(Modifier.height(8.dp))
                Text("Priority: ${report.priority}", style = MaterialTheme.typography.labelSmall)

                Spacer(Modifier.height(16.dp))

                Button(
                    onClick = { /* Implement Google Maps Navigation Intent */ },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Text("Start Navigation", color = Color.White)
                }
            }
        }
    }
}