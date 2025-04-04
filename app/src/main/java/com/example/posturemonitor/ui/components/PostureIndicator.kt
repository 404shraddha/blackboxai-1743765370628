package com.example.posturemonitor.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
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
import com.example.posturemonitor.ble.PostureStatus
import com.example.posturemonitor.ui.theme.BadPostureRed
import com.example.posturemonitor.ui.theme.GoodPostureGreen
import com.example.posturemonitor.ui.theme.WarningPostureOrange

@Composable
fun PostureIndicator(
    status: PostureStatus,
    modifier: Modifier = Modifier
) {
    val (color, icon, text) = when (status) {
        PostureStatus.GOOD -> Triple(
            GoodPostureGreen,
            painterResource(R.drawable.ic_posture_good),
            stringResource(R.string.good_posture)
        )
        PostureStatus.WARNING -> Triple(
            WarningPostureOrange,
            painterResource(R.drawable.ic_posture_warning),
            stringResource(R.string.warning_posture)
        )
        PostureStatus.BAD -> Triple(
            BadPostureRed,
            painterResource(R.drawable.ic_posture_bad),
            stringResource(R.string.bad_posture)
        )
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Icon(
            painter = icon,
            contentDescription = null,
            tint = color,
            modifier = Modifier.size(64.dp)
        )
        Text(
            text = text,
            style = MaterialTheme.typography.titleLarge,
            color = color
        )
    }
}