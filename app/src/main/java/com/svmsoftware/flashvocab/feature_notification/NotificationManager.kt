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
import com.svmsoftware.flashvocab.core.domain.model.UserSettings
import com.svmsoftware.flashvocab.core.domain.repository.BookmarkRepository
import com.svmsoftware.flashvocab.core.domain.repository.SettingRepository
import com.svmsoftware.flashvocab.core.domain.use_cases.ProcessTranslate
import com.svmsoftware.flashvocab.core.presentation.MainActivity
import com.svmsoftware.flashvocab.core.util.Resource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NotificationManager @Inject constructor(
    private val settingRepository: SettingRepository,
    private val notificationBuilder: NotificationCompat.Builder,
    private val notificationManager: NotificationManagerCompat,
    @ApplicationContext private val applicationContext: Context,
) {

    private val _state = MutableStateFlow(NotificationState())
    val state: StateFlow<NotificationState> = _state

    fun performTranslationNotification(word: String?) {
        CoroutineScope(Dispatchers.IO).launch {
            getSettings()
            processTranslate(word)
        }
    }

    private suspend fun getSettings() {
        val result = settingRepository.getSettings().first()
        _state.value = _state.value.copy(
            userSettings = result
        )
    }

    private fun processTranslate(word: String?) {
        if (word.isNullOrEmpty()) {
        } else {
            val result = ProcessTranslate().invoke(
                source = word, targetLang = state.value.userSettings?.translatedLangCode!!
            )
            when (result) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        textToTranslate = word, translatedText = result.data!!,
                    )
                    showTranslationSuccessNotification()
                }

                is Resource.Error -> {
                    showTranslationWarningNotification()
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
                putExtra("action", "save_bookmarks")
                putExtra("source", state.value.textToTranslate)
                putExtra("target", state.value.translatedText)
                putExtra("sourceLang", state.value.sourceLanguageCode)
                putExtra("targetLang", state.value.targetLanguageCode)
            },
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) FLAG_IMMUTABLE else 0
        )

        val textToSpeechIntent = PendingIntent.getBroadcast(
            applicationContext,
            1,
            Intent(applicationContext, NotificationReceiver::class.java).apply {
                putExtra("action", "textToSpeech")
            },
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) FLAG_IMMUTABLE else 0
        )

        val textToTranslate = state.value.textToTranslate
        val translatedText = state.value.translatedText

        val notificationContent = """
              <div style="display: flex; align-items: center;">
                <b style="margin-left: 10px;">$textToTranslate</b>
                <br>
                <span style="margin-left: 10px;">$translatedText</span>
              </div>
           """.trimIndent()

        notificationManager.notify(
            1, notificationBuilder.setStyle(
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
    private fun showTranslationWarningNotification() {

        val notificationContent = """
              <div style="display: flex; align-items: center;">
                <b style="margin-left: 10px;">There is something wrong</b>
                <br>
              </div>
           """.trimIndent()

        notificationManager.notify(
            1, notificationBuilder.setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(Html.fromHtml(notificationContent, Html.FROM_HTML_MODE_COMPACT))
            ).build()
        )
    }
}
