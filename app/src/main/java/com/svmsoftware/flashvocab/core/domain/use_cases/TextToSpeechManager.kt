package com.svmsoftware.flashvocab.core.domain.use_cases

import android.content.Context
import android.speech.tts.TextToSpeech
import java.util.Locale
import javax.inject.Inject

class TextToSpeechManager @Inject constructor(
    private val context: Context
) {

    private var textToSpeech: TextToSpeech? = null

    fun speak(text: String, langCode: String) {
        textToSpeech = TextToSpeech(
            context
        ) {

            if (it == TextToSpeech.SUCCESS) {
                textToSpeech?.let { txtToSpeech ->
                    txtToSpeech.setLanguage(Locale(langCode))
                    txtToSpeech.setSpeechRate(1.0f)
                    txtToSpeech.speak(
                        text, TextToSpeech.QUEUE_ADD, null, null
                    )
                }
            }
        }
    }

    fun shutdown() {
        textToSpeech?.shutdown()
    }
}