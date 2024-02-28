package com.svmsoftware.flashvocab.feature.home


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.svmsoftware.flashvocab.core.design_system.theme.MidnightBlue
import com.svmsoftware.flashvocab.core.design_system.theme.VividBlue
import com.svmsoftware.flashvocab.core.domain.TextToSpeech
import com.svmsoftware.flashvocab.feature.home.component.LanguageSelector
import com.svmsoftware.flashvocab.feature.home.component.TextInputField

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {

    var value by remember {
        mutableStateOf("")
    }

    Column(
        modifier
            .fillMaxSize()
            .padding(vertical = 36.dp, horizontal = 32.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            TextInputField(modifier = Modifier.fillMaxWidth(),
                background = MidnightBlue,
                value = value,
                language = "Indonesia",
                onSoundClick = {},
                onValueChange = { s -> value = s })
            Spacer(modifier = Modifier.height(32.dp))
            TextInputField(
                modifier = Modifier.fillMaxWidth(),
                labelColor = Color.White,
                background = VividBlue,
                readOnly = true,
                value = value,
                language = "English",
                onSoundClick = {
                },
                onValueChange = null
            )
        }
        LanguageSelector(
            modifier = Modifier.fillMaxWidth()
        )
    }
}

