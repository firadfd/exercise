package fd.firad.exercise

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val context = this
            var imageUrl by remember { mutableStateOf("") }
            val coroutineScope = rememberCoroutineScope()

            // Fetch the last imageUrl on app launch
            LaunchedEffect(Unit) {
                val savedUrl = getLastSavedImageUrl(context)
                imageUrl = savedUrl ?: ""
            }

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                if (imageUrl.isNotEmpty()) {
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = "Fetched Image",
                        modifier = Modifier
                            .height(200.dp)
                            .width(200.dp)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = {
                    coroutineScope.launch {
                        if (isNetworkAvailable(context)) {
                            val randomImageUrl = fetchRandomImageUrl()
                            imageUrl = randomImageUrl
                            //save imageUrl
                            saveImageUrl(context, randomImageUrl)
                        } else {
                            Toast.makeText(
                                context,
                                "No connection available. Showing last cached image.",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }) {
                    Text("Fetch New Image")
                }
            }
        }
    }

}

fun fetchRandomImageUrl(): String {
    return "https://picsum.photos/200/300?random=${System.currentTimeMillis()}"
}



