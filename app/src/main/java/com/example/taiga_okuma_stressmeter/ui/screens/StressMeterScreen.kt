import android.content.Context
import android.media.RingtoneManager
import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
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

    // Start and stop the sound with proper lifecycle management
    DisposableEffect(Unit) {
        playSound(context)  // Play looping sound when entering the screen

        onDispose {
            stopSound()  // Stop the sound when exiting the screen
        }
    }

    // Load the stress data when the screen is opened
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            stressViewModel.loadStressData(context)
        }
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
                        itemsIndexed(stressViewModel.currentImages) { _, imageResId ->
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

// Play sound function using built-in notification sound with loop
private var ringtone: android.media.Ringtone? = null
private var handler: Handler? = null

private fun playSound(context: Context) {
    val notificationUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
    ringtone = RingtoneManager.getRingtone(context, notificationUri)

    handler = Handler(Looper.getMainLooper())

    // Function to play the ringtone in a loop
    fun playRingtoneInLoop() {
        ringtone?.play()

        // Schedule the next play based on the ringtone's duration
        handler?.postDelayed({
            if (ringtone?.isPlaying == false) {
                playRingtoneInLoop()  // Replay the ringtone when it finishes
            }
        }, 1000)  // Adjust this delay to match the sound length for a smoother loop
    }

    playRingtoneInLoop()
}

// Stop the looping sound
private fun stopSound() {
    ringtone?.stop()
    handler?.removeCallbacksAndMessages(null)  // Stop the handler
    ringtone = null
    handler = null
}
