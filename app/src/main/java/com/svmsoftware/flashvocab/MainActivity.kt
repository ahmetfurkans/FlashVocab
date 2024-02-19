package com.svmsoftware.flashvocab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.svmsoftware.flashvocab.core.design_system.theme.FlashVocabTheme
import com.svmsoftware.flashvocab.core.design_system.theme.MidnightBlack
import com.svmsoftware.flashvocab.core.navigation.BottomNavigationBar
import com.svmsoftware.flashvocab.core.navigation.SetupNavGraph

class MainActivity : ComponentActivity(
) {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {

            val navController = rememberNavController()

            FlashVocabTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MidnightBlack
                ) {
                    Scaffold(
                        bottomBar = { BottomNavigationBar(navController = navController) }
                    ) {
                        SetupNavGraph(Modifier.padding(it), navController)
                    }
                }
            }
        }
    }
}
