import android.Manifest
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.taiga_okuma_stressmeter.MainActivity
import com.example.taiga_okuma_stressmeter.R
import com.example.taiga_okuma_stressmeter.ui.viewmodel.StressViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StressMeterScreen(
    stressViewModel: StressViewModel = viewModel(),
    onImageClick: (Int) -> Unit,
    context: Context
) {
    val coroutineScope = rememberCoroutineScope()

    // Create notification channel and other side effects when the screen is opened
    LaunchedEffect(Unit) {
        (context as? MainActivity)?.createNotificationChannel()  // Create notification channel in the main activity
        sendNotification(context)  // Send notification
        playSound(context)  // Play sound when screen opens
        vibratePhone(context)  // Vibrate phone when screen opens
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Stress Meter") }
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        "Touch the image that best captures how stressed you feel right now",
                        modifier = Modifier.padding(16.dp)
                    )

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(4),
                        modifier = Modifier.fillMaxWidth().padding(4.dp),
                        contentPadding = PaddingValues(4.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        itemsIndexed(stressViewModel.currentImages) { index, imageResId ->
                            Image(
                                painter = painterResource(id = imageResId),
                                contentDescription = "Stress Image $imageResId",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .aspectRatio(1f)
                                    .clickable {
                                        onImageClick(imageResId)
                                    }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // "More Images" button
                Button(
                    onClick = {
                        coroutineScope.launch {
                            stressViewModel.shuffleImages()  // Shuffle images
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text("More Images")
                }
            }
        }
    )
}

fun sendNotification(context: Context) {
    val channelId = "stress_notification_channel"
    val notificationId = 1

    // Create an explicit intent for an Activity
    val intent = Intent(context, MainActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }
    val pendingIntent: PendingIntent = PendingIntent.getActivity(
        context,
        0,
        intent,
        PendingIntent.FLAG_IMMUTABLE
    )

    val builder = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(R.drawable.ic_launcher_foreground)  // Add your icon here
        .setContentTitle("Stress Meter Notification")
        .setContentText("Welcome to the Stress Meter! Select a stress level.")
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)  // Dismiss the notification after the user taps it

    // Check for notification permission on Android 13+
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Request the POST_NOTIFICATIONS permission if not granted
            ActivityCompat.requestPermissions(
                (context as MainActivity),
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                1001  // Request code, can be any unique integer
            )
            return  // Exit the function until permission is granted
        }
    }

    // Show the notification if permissions are already granted
    with(NotificationManagerCompat.from(context)) {
        notify(notificationId, builder.build())
    }
}

// Vibrate phone function
@RequiresApi(Build.VERSION_CODES.O)
private fun vibratePhone(context: Context) {
    val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    if (vibrator.hasVibrator()) {
        val vibrationEffect = VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE)
        vibrator.vibrate(vibrationEffect)
    }
}

// Play sound function using built-in notification sound
private fun playSound(context: Context) {
    val notificationUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)  // Use system notification sound
    val ringtone = RingtoneManager.getRingtone(context, notificationUri)
    ringtone.play()
}
