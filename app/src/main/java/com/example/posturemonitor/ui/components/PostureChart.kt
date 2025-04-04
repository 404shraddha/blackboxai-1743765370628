package com.example.posturemonitor.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.patrykandpatrick.vico.compose.axis.horizontal.bottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.startAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.compose.component.shape.shader.fromComponent
import com.patrykandpatrick.vico.compose.style.ProvideChartStyle
import com.patrykandpatrick.vico.core.axis.AxisPosition
import com.patrykandpatrick.vico.core.axis.formatter.AxisValueFormatter
import com.patrykandpatrick.vico.core.component.shape.LineComponent
import com.patrykandpatrick.vico.core.component.shape.Shapes
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatrick.vico.core.entry.FloatEntry
import kotlin.math.roundToInt

@Composable
fun PostureChart(
    dataPoints: List<Float>,
    modifier: Modifier = Modifier
) {
    val entries = dataPoints.mapIndexed { index, value -> 
        FloatEntry(index.toFloat(), value)
    }
    val modelProducer = ChartEntryModelProducer(entries)

    ProvideChartStyle {
        Chart(
            chart = lineChart(
                lines = listOf(
                    LineComponent(
                        color = MaterialTheme.colorScheme.primary,
                        thickness = 4.dp,
                        shape = Shapes.cutCornerShape(topLeftPercent = 50),
                        backgroundShader = LineComponent.shaderFromComponent(
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                            componentShape = Shapes.roundedCornerShape(8.dp)
                        )
                    )
                )
            ),
            chartModelProducer = modelProducer,
            modifier = modifier
                .fillMaxWidth()
                .height(200.dp),
            startAxis = startAxis(
                title = "Tilt Angle (°)",
                valueFormatter = AxisValueFormatter<AxisPosition.Vertical.Start> { value, _ ->
                    "${value.roundToInt()}°"
                }
            ),
            bottomAxis = bottomAxis(
                title = "Time",
                valueFormatter = AxisValueFormatter<AxisPosition.Horizontal.Bottom> { value, _ ->
                    "${value.roundToInt()}s"
                }
            )
        )
    }
}