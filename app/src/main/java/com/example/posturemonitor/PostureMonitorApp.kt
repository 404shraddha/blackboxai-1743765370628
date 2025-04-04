package com.example.posturemonitor

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.posturemonitor.navigation.AppNavHost
import com.example.posturemonitor.ui.theme.PostureMonitorTheme

@Composable
fun PostureMonitorApp() {
    PostureMonitorTheme {
        val navController = rememberNavController()
        AppNavHost(navController = navController)
    }
}