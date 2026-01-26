package com.roshnab.aasra.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.roshnab.aasra.auth.AuthScreen
import com.roshnab.aasra.screens.DonationScreen
import com.roshnab.aasra.screens.HomeScreen
import com.roshnab.aasra.screens.ReportScreen

@Composable
fun AasraNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "auth") {

        composable("auth") {
            AuthScreen(onAuthSuccess = {
                navController.navigate("home") { popUpTo("auth") { inclusive = true } }
            })
        }

        composable("home") {
            HomeScreen(
                onReportClick = { lat, lng ->
                    navController.navigate("report/${lat.toFloat()}/${lng.toFloat()}")
                },

                onDonationClick = {
                    navController.navigate("donation")
                }
            )
        }

        composable("donation") {
            DonationScreen(onBackClick = { navController.popBackStack() })
        }

        composable(
            route = "report/{lat}/{lng}",
            arguments = listOf(
                navArgument("lat") { type = NavType.FloatType },
                navArgument("lng") { type = NavType.FloatType }
            )
        ) { backStackEntry ->
            val lat = backStackEntry.arguments?.getFloat("lat")?.toDouble() ?: 0.0
            val lng = backStackEntry.arguments?.getFloat("lng")?.toDouble() ?: 0.0

            ReportScreen(
                latitude = lat,
                longitude = lng,
                onBackClick = { navController.popBackStack() },
                onSubmitClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}