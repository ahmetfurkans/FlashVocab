package com.svmsoftware.flashvocab.core.domain.use_cases

import android.content.Context
import android.speech.tts.TextToSpeech
import java.util.Locale


class TextToSpeech {
    private var textToSpeech: TextToSpeech? = null
    operator fun invoke(text: String, context: Context) {
        textToSpeech = TextToSpeech(
            context
        ) {
            if (it == TextToSpeech.SUCCESS) {
                textToSpeech?.let { txtToSpeech ->
                    txtToSpeech.language = Locale.US
                    txtToSpeech.setSpeechRate(1.0f)
                    txtToSpeech.speak(
                        text, TextToSpeech.QUEUE_ADD, null, null
                    )
                }
            }
        }
    }
}