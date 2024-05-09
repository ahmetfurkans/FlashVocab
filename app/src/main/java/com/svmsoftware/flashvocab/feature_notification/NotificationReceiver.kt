package com.svmsoftware.flashvocab.feature_notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.svmsoftware.flashvocab.core.domain.model.Bookmark
import com.svmsoftware.flashvocab.core.domain.repository.BookmarkRepository
import com.svmsoftware.flashvocab.core.domain.use_cases.TextToSpeech
import com.svmsoftware.flashvocab.core.domain.use_cases.TextToSpeechManager
import com.svmsoftware.flashvocab.feature_notification.NotificationManager.Companion.ActionExtra
import com.svmsoftware.flashvocab.feature_notification.NotificationManager.Companion.OriginalTextExtra
import com.svmsoftware.flashvocab.feature_notification.NotificationManager.Companion.SourceLanguageCodeExtra
import com.svmsoftware.flashvocab.feature_notification.NotificationManager.Companion.TargetLanguageCodeExtra
import com.svmsoftware.flashvocab.feature_notification.NotificationManager.Companion.TranslatedTextExtra
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class NotificationReceiver : BroadcastReceiver() {

    @Inject
    lateinit var bookmarkRepository: BookmarkRepository

    @Inject
    lateinit var textToSpeechManager: TextToSpeechManager

    private var firstSaveFlag = false

    override fun onReceive(context: Context?, intent: Intent?) {

        val action = intent?.getStringExtra(ActionExtra)
        val originalText = intent?.getStringExtra(OriginalTextExtra)
        val translatedText = intent?.getStringExtra(TranslatedTextExtra)
        val sourceLangCode = intent?.getStringExtra(SourceLanguageCodeExtra)
        val targetLangCode = intent?.getStringExtra(TargetLanguageCodeExtra)

        when (action?.let { NotificationManager.Actions.valueOf(it) }) {
            NotificationManager.Actions.Save -> {
                val bookmark = Bookmark(
                    originalText = originalText!!,
                    translatedText = translatedText!!,
                    targetLanguage = targetLangCode!!,
                    sourceLanguage = sourceLangCode!!,
                    time = System.currentTimeMillis(),
                )
                saveTranslation(bookmark = bookmark)
                firstSaveFlag = true
            }

            NotificationManager.Actions.TextToSpeech -> {
                textToSpeech(originalText!!, sourceLangCode!!)
            }

            else -> {}
        }
    }

    private fun saveTranslation(bookmark: Bookmark) {
        CoroutineScope(Dispatchers.IO).launch {
            bookmarkRepository.insertBookmark(
                bookmark
            )
        }
    }

    private fun textToSpeech(text: String, languageCode: String) {
        textToSpeechManager.shutdown()
        textToSpeechManager.speak(text, languageCode)
    }

}