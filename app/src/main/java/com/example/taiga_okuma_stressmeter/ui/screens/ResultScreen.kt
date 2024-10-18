import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taiga_okuma_stressmeter.data.StressData
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ResultScreen(stressData: List<StressData>) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(scrollState)  // Enable scrolling
    ) {
        if (stressData.isEmpty()) {
            // Show this message if there's no stress data to display
            Text(text = "No stress data available.", fontSize = 18.sp)
        } else {
            // If stress data exists, show the graph and results
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
}

// Helper function to convert timestamp to a readable date and time
fun formatTimestamp(timestamp: String): String {
    val sdf = SimpleDateFormat("dd MMM, HH:mm", Locale.getDefault())
    val date = Date(timestamp.toLong())
    return sdf.format(date)
}

@Composable
fun StressLevelLineChart(stressData: List<StressData>) {
    if (stressData.isEmpty()) {
        Text(text = "No stress data available.")
        return
    }

    val maxStress = stressData.maxOf { it.stressLevel }.toFloat()
    val minStress = stressData.minOf { it.stressLevel }.toFloat()
    val stressRange = maxStress - minStress

    // Ensure layout management for title, X/Y axis, and the graph itself
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        // Title
        Text(
            text = "A graph showing your stress levels",
            fontSize = 18.sp,
            modifier = Modifier.padding(bottom = 8.dp),
            textAlign = TextAlign.Center
        )

        Row {
            // Y-axis Label
            Text(
                text = "Stress Level",
                fontSize = 12.sp,
                modifier = Modifier
                    .rotate(-90f)  // Rotate the text for vertical axis
                    .align(Alignment.CenterVertically)
                    .padding(end = 8.dp)
            )

            // Graph Area
            Box(
                modifier = Modifier
                    .height(300.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val width = size.width
                    val height = size.height
                    val xStep = width / (stressData.size - 1)
                    val yStep = height / 5

                    // Draw grid lines
                    for (i in 0..5) {
                        drawLine(
                            Color.LightGray,
                            start = Offset(0f, i * yStep),
                            end = Offset(width, i * yStep),
                            strokeWidth = 1f
                        )
                    }

                    // Create path for the line chart
                    val path = Path().apply {
                        stressData.forEachIndexed { index, data ->
                            val x = index * xStep
                            val y = height - ((data.stressLevel - minStress) / stressRange) * height
                            if (index == 0) {
                                moveTo(x, y) // Start the line at the first data point
                            } else {
                                lineTo(x, y) // Draw a straight line to each subsequent point
                            }
                        }
                    }

                    // Draw filled area below the line
                    drawPath(
                        path = path.apply {
                            lineTo(width, height) // Connect to bottom-right corner
                            lineTo(0f, height) // Connect to bottom-left corner
                            close() // Close the path to form the area shape
                        },
                        color = Color(0xFF6200EE).copy(alpha = 0.15f) // Light fill color
                    )

                    // Draw the line on top of the filled area
                    drawPath(
                        path = path,
                        color = Color(0xFF6200EE), // Line color
                        style = Stroke(width = 2.5f) // Line stroke width
                    )

                    // Draw points on the line
                    stressData.forEachIndexed { index, data ->
                        val x = index * xStep
                        val y = height - ((data.stressLevel - minStress) / stressRange) * height
                        drawCircle(Color(0xFF6200EE), radius = 6f, center = Offset(x, y)) // Larger points for visibility
                    }
                }

                // Y-axis labels (inside the graph box)
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(start = 8.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    for (i in 5 downTo 0) {
                        Text(
                            text = ((maxStress - minStress) * i / 5 + minStress).toInt().toString(),
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }
                }
            }
        }

        // X-axis Label
        Text(
            text = "Date and Time",
            fontSize = 12.sp,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 8.dp)
        )
    }
}

