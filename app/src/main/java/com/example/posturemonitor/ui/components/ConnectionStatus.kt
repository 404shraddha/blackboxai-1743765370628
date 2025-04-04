package com.example.posturemonitor.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.posturemonitor.R
import com.example.posturemonitor.ui.home.HomeViewModel.ConnectionState

@Composable
fun ConnectionStatus(
    state: ConnectionState,
    modifier: Modifier = Modifier
) {
    val (iconRes, textRes, color) = when (state) {
        is ConnectionState.Connected -> Triple(
            R.drawable.ic_bluetooth_connected,
            R.string.connected,
            MaterialTheme.colorScheme.primary
        )
        is ConnectionState.Connecting -> Triple(
            R.drawable.ic_bluetooth_searching,
            R.string.connecting,
            MaterialTheme.colorScheme.secondary
        )
        is ConnectionState.Scanning -> Triple(
            R.drawable.ic_bluetooth_searching,
            R.string.scanning,
            MaterialTheme.colorScheme.secondary
        )
        is ConnectionState.Error -> Triple(
            R.drawable.ic_bluetooth_disabled,
            R.string.error,
            MaterialTheme.colorScheme.error
        )
        is ConnectionState.Disconnected -> Triple(
            R.drawable.ic_bluetooth_disabled,
            R.string.disconnected,
            MaterialTheme.colorScheme.error
        )
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.padding(8.dp)
    ) {
        Icon(
            painter = painterResource(iconRes),
            contentDescription = null,
            tint = color
        )
        Text(
            text = if (state is ConnectionState.Error) 
                stringResource(textRes, state.message) 
            else 
                stringResource(textRes),
            color = color,
            style = MaterialTheme.typography.labelLarge
        )
    }
}