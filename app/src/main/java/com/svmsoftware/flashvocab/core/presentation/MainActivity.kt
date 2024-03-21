package com.svmsoftware.flashvocab.core.presentation

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
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.svmsoftware.flashvocab.core.presentation.navigation.BottomNavigationBar
import com.svmsoftware.flashvocab.core.presentation.navigation.SetupNavGraph
import com.svmsoftware.flashvocab.core.presentation.theme.FlashVocabTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity(
) {
    @RequiresApi(Build.VERSION_CODES.O)
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
}
