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

@Composable
fun BatteryIndicator(
    level: Int,
    modifier: Modifier = Modifier
) {
    val iconRes = when {
        level > 75 -> R.drawable.ic_battery_full
        level > 50 -> R.drawable.ic_battery_medium
        level > 25 -> R.drawable.ic_battery_low
        else -> R.drawable.ic_battery_alert
    }

    val color = when {
        level > 25 -> MaterialTheme.colorScheme.primary
        else -> MaterialTheme.colorScheme.error
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
            text = stringResource(R.string.battery_level, level),
            color = color,
            style = MaterialTheme.typography.labelLarge
        )
    }
}