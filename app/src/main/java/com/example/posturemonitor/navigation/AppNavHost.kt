package com.example.posturemonitor.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.posturemonitor.ui.home.HomeScreen
import com.example.posturemonitor.ui.onboarding.OnboardingScreen

@Composable
fun AppNavHost(
    navController: androidx.navigation.NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = "onboarding"
    ) {
        composable("onboarding") {
            OnboardingScreen(
                onContinue = { navController.navigate("home") }
            )
        }
        composable("home") {
            HomeScreen()
        }
    }
}