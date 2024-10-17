import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class StressData(
    val timestamp: String,
    val stressLevel: Int
)

@Composable
fun ResultScreen(stressData: List<StressData>) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "A graph showing your Stress Levels",
            modifier = Modifier.padding(8.dp),
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        StressLevelLineChart(stressData)
        Spacer(modifier = Modifier.height(16.dp))
        Text("Summary of Results", fontSize = 16.sp)
        Spacer(modifier = Modifier.height(8.dp))
        stressData.forEach { (timestamp, stressLevel) ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(timestamp)
                Text(stressLevel.toString())
            }
        }
    }
}

@Composable
fun StressLevelLineChart(stressData: List<StressData>) {
    if (stressData.isEmpty()) {
        Text(text = "No stress data available.")
        return
    }

    val maxStress = stressData.maxOf { it.stressLevel }.toFloat()
    val minStress = stressData.minOf { it.stressLevel }.toFloat()

    Box(
        modifier = Modifier
            .height(200.dp)
            .fillMaxWidth()
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height
            val xStep = width / (stressData.size - 1)

            // Draw axes
            drawLine(Color.Gray, Offset(0f, height), Offset(width, height))
            drawLine(Color.Gray, Offset(0f, 0f), Offset(0f, height))

            // Draw the line chart
            val path = Path()
            stressData.forEachIndexed { index, data ->
                val x = index * xStep
                val y = height - (data.stressLevel - minStress) / (maxStress - minStress) * height
                if (index == 0) {
                    path.moveTo(x, y)
                } else {
                    path.lineTo(x, y)
                }
                // Draw points
                drawCircle(Color.Blue, 4f, Offset(x, y))
            }
            // Draw the line
            drawPath(path, Color.Blue, style = Stroke(width = 2f))
        }

        // Y-axis labels
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = maxStress.toInt().toString(), fontSize = 10.sp)
            Text(text = minStress.toInt().toString(), fontSize = 10.sp)
        }
    }
}