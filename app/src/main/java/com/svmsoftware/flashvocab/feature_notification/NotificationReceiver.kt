package com.svmsoftware.flashvocab.feature_notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.svmsoftware.flashvocab.core.domain.model.Bookmark
import com.svmsoftware.flashvocab.core.domain.repository.BookmarkRepository
import com.svmsoftware.flashvocab.core.domain.use_cases.TextToSpeech
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
    lateinit var textToSpeech: TextToSpeech

    override fun onReceive(context: Context?, intent: Intent?) {

        val action = intent?.getStringExtra(ActionExtra)
        val source = intent?.getStringExtra(TranslatedTextExtra)
        val target = intent?.getStringExtra(OriginalTextExtra)
        val sourceLang = intent?.getStringExtra(SourceLanguageCodeExtra)
        val targetLang = intent?.getStringExtra(TargetLanguageCodeExtra)

        val bookmark = Bookmark(
            sourceText = target!!,
            targetText = source!!,
            targetLanguage = targetLang!!,
            sourceLanguage = sourceLang!!,
            time = System.currentTimeMillis(),
        )

        when (action?.let { NotificationManager.Actions.valueOf(it) }) {
            NotificationManager.Actions.Save -> {
                saveTranslation(bookmark = bookmark)
            }

            NotificationManager.Actions.TextToSpeech -> {
                textToSpeech(source)
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

    private fun textToSpeech(text: String) {
        textToSpeech.invoke(text)
    }

}