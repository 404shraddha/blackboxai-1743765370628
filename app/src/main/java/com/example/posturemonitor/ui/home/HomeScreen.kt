package com.example.posturemonitor.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.*
import androidx.compose.ui.unit.dp
import com.example.posturemonitor.ui.components.PostureChart
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.posturemonitor.ui.components.BatteryIndicator
import com.example.posturemonitor.ui.components.ConnectionStatus
import com.example.posturemonitor.ui.components.PostureIndicator
import com.example.posturemonitor.ui.theme.PostureMonitorTheme

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val connectionState by viewModel.connectionState.collectAsState()
    val postureData by viewModel.postureData.collectAsState()
    val batteryLevel by viewModel.batteryLevel.collectAsState()
    val postureHistory by viewModel.postureHistory.collectAsState()

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ConnectionStatus(state = connectionState)
                batteryLevel?.let { level ->
                    BatteryIndicator(level = level)
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            postureData?.let { data ->
                PostureIndicator(status = data.status)
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Tilt: ${"%.1f".format(data.tiltAngle)}°",
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.height(16.dp))
                PostureChart(
                    dataPoints = postureHistory,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(horizontal = 16.dp)
                )
            } ?: run {
                Text(
                    text = "Waiting for posture data...",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    PostureMonitorTheme {
        HomeScreen()
    }
}
