package com.svmsoftware.flashvocab.feature_notification

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.content.Context
import android.content.Intent
import android.os.Build
import android.text.Html
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.svmsoftware.flashvocab.R
import com.svmsoftware.flashvocab.core.domain.model.Bookmark
import com.svmsoftware.flashvocab.core.domain.repository.BookmarkRepository
import com.svmsoftware.flashvocab.core.domain.repository.SettingRepository
import com.svmsoftware.flashvocab.core.domain.use_cases.ProcessTranslate
import com.svmsoftware.flashvocab.core.domain.use_cases.TextToSpeechManager
import com.svmsoftware.flashvocab.core.util.Resource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

class NotificationManager @Inject constructor(
    private val settingRepository: SettingRepository,
    private val bookmarkRepository: BookmarkRepository,
    private val textToSpeechManager: TextToSpeechManager,
    private val notificationBuilder: NotificationCompat.Builder,
    private val notificationManager: NotificationManagerCompat,
    @ApplicationContext private val applicationContext: Context,
) {

    companion object {
        const val ActionExtra = "action"
        const val OriginalTextExtra = "originalText"
        const val TranslatedTextExtra = "translatedText"
        const val SourceLanguageCodeExtra = "sourceLanguageCode"
        const val TargetLanguageCodeExtra = "targetLanguageCode"
    }

    enum class Actions {
        Save, Discard, TextToSpeech
    }

    private val _state = MutableStateFlow(NotificationState())
    val state: StateFlow<NotificationState> = _state

    fun performTranslationNotification(word: String?) {
        CoroutineScope(Dispatchers.IO).launch {
            getSettings()
            processTranslate(word)
            if (state.value.userSettings?.isAutoSaveEnabled!!) {
                saveTranslation()
                _state.value = state.value.copy(
                    isAlreadySaved = true
                )
            }
            if (state.value.userSettings?.isAutoReadEnabled!!) {
                textToSpeechManager.shutdown()
                textToSpeechManager.speak(state.value.originalText, state.value.sourceLanguageCode)
            }
        }
    }

    private suspend fun getSettings() {
        val result = settingRepository.getSettings().first()
        _state.value = _state.value.copy(
            userSettings = result
        )
    }

    private fun processTranslate(word: String?) {
        if (!word.isNullOrEmpty()) {
            val result = ProcessTranslate().invoke(
                source = word, targetLang = state.value.userSettings?.translatedLangCode!!
            )
            when (result) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        originalText = word,
                        translatedText = result.data?.translatedText!!,
                        sourceLanguageCode = result.data.sourceLanguage,
                        targetLanguageCode = state.value.userSettings?.translatedLangCode!!
                    )
                    showTranslationSuccessNotification()
                }

                is Resource.Error -> {
                    showTranslationWarningNotification(result.desc!!)
                }
            }
        }
    }


    @SuppressLint("MissingPermission")
    private fun showTranslationSuccessNotification() {
        val saveBookmarksIntent = PendingIntent.getBroadcast(
            applicationContext,
            2,
            Intent(applicationContext, NotificationReceiver::class.java).apply {

                putExtra(ActionExtra, Actions.Save.name)
                putExtra(OriginalTextExtra, state.value.originalText)
                putExtra(TranslatedTextExtra, state.value.translatedText)
                putExtra(SourceLanguageCodeExtra, state.value.sourceLanguageCode)
                putExtra(TargetLanguageCodeExtra, state.value.targetLanguageCode)
                _state.value = state.value.copy(
                    isAlreadySaved = true
                )
            },
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT else 0,
        )

        val textToSpeechIntent = PendingIntent.getBroadcast(
            applicationContext,
            1,
            Intent(applicationContext, NotificationReceiver::class.java).apply {
                putExtra(ActionExtra, Actions.TextToSpeech.name)
                putExtra(OriginalTextExtra, state.value.originalText)
                putExtra(SourceLanguageCodeExtra, state.value.sourceLanguageCode)
            },
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT else 0
        )


        val textToTranslate = state.value.originalText
        val translatedText = state.value.translatedText

        val notificationContent = """
              <div style="display: flex; align-items: center;">
                <b style="margin-left: 10px;">$textToTranslate</b>
                <br>
                <span style="margin-left: 10px;">$translatedText</span>
              </div>
           """.trimIndent()

        notificationManager.notify(
            1,
            notificationBuilder.setContentText(
                Html.fromHtml(
                    notificationContent,
                    Html.FROM_HTML_MODE_COMPACT
                )
            ).setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(Html.fromHtml(notificationContent, Html.FROM_HTML_MODE_COMPACT))
            ).addAction(
                R.drawable.italian, "Add Bookmarks", saveBookmarksIntent
            ).addAction(
                R.drawable.italian, "Text To Speech", textToSpeechIntent
            ).build()
        )
    }

    @SuppressLint("MissingPermission")
    private fun showTranslationWarningNotification(description: String) {

        val notificationContent = """
              <div style="display: flex; align-items: center;">
                <b style="margin-left: 10px;">$description</b>
                <br>
              </div>
           """.trimIndent()

        notificationManager.notify(
            1,
            notificationBuilder.setContentText(
                Html.fromHtml(
                    notificationContent,
                    Html.FROM_HTML_MODE_COMPACT
                )
            ).setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(Html.fromHtml(notificationContent, Html.FROM_HTML_MODE_COMPACT))
            ).build()
        )
    }

    private fun saveTranslation() {
        val bookmark = Bookmark(
            originalText = state.value.originalText,
            translatedText = state.value.translatedText,
            targetLanguage = state.value.targetLanguageCode,
            sourceLanguage = state.value.sourceLanguageCode,
            time = System.currentTimeMillis(),
        )

        CoroutineScope(Dispatchers.IO).launch {
            bookmarkRepository.insertBookmark(
                bookmark
            )
        }
    }
}
