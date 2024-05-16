package com.svmsoftware.flashvocab.core.presentation

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.svmsoftware.flashvocab.core.presentation.navigation.BottomNavigationBar
import com.svmsoftware.flashvocab.core.presentation.navigation.SetupNavGraph
import com.svmsoftware.flashvocab.core.presentation.theme.FlashVocabTheme
import com.svmsoftware.flashvocab.core.util.Constants.CHANNEL_ID
import com.svmsoftware.flashvocab.core.util.Constants.CHANNEL_NAME
import dagger.hilt.android.AndroidEntryPoint
import java.lang.RuntimeException


@AndroidEntryPoint
class MainActivity : ComponentActivity(
) {
    @RequiresApi(Build.VERSION_CODES.O)
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        createNotificationChannel()

        setContent {
            val navController = rememberNavController()
            val snackbarHostState = remember { SnackbarHostState() }

            FlashVocabTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Scaffold(
                        bottomBar = { BottomNavigationBar(navController = navController) },
                        snackbarHost = { SnackbarHost(snackbarHostState) },
                    ) {
                        SetupNavGraph(
                            Modifier.padding(it),
                            navController,
                            snackbarHostState = snackbarHostState
                        )
                    }
                }
            }
        }
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is not in the Support Library.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = CHANNEL_NAME
            val descriptionText = CHANNEL_NAME
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system.
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

}

