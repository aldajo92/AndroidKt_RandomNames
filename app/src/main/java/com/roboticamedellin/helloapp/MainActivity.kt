package com.roboticamedellin.helloapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import com.roboticamedellin.helloapp.ui.theme.HelloAppTheme

class MainActivity : ComponentActivity() {

    private val intent2 by lazy {
        Intent(this, RandomNameService::class.java)
    }

    private val randomNameReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val receivedName = intent?.getStringExtra("randomName")
            randomName = receivedName ?: "Unknown"
        }
    }

    // Mutable state for Compose to react to
    private var randomName by mutableStateOf("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Start the service
        startService(intent2)

        // Register the BroadcastReceiver with RECEIVER_NOT_EXPORTED
        ContextCompat.registerReceiver(
            this,
            randomNameReceiver,
            IntentFilter("com.example.RandomNameBroadcast"),
            ContextCompat.RECEIVER_NOT_EXPORTED
        )

        setContent {
            HelloAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.fillMaxSize()) {
                        Greeting(
                            modifier = Modifier
                                .padding(innerPadding)
                                .align(Alignment.Center),
                            name = randomName.ifEmpty { "No name received yet" }
                        )
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Stop the service
        stopService(intent2)

        // Unregister the BroadcastReceiver
        unregisterReceiver(randomNameReceiver)
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HelloAppTheme {
        Greeting("Android")
    }
}