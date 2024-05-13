package com.svmsoftware.flashvocab.feature_notification

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_CANCEL_CURRENT
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.RECEIVER_EXPORTED
import android.content.Context.RECEIVER_NOT_EXPORTED
import android.content.Intent
import android.content.IntentFilter
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
import com.svmsoftware.flashvocab.core.util.Constants.CHANNEL_ID
import com.svmsoftware.flashvocab.core.util.Resource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

class NotificationManager @Inject constructor(
    private val settingRepository: SettingRepository,
    private val bookmarkRepository: BookmarkRepository,
    private val textToSpeechManager: TextToSpeechManager,
    @ApplicationContext private val applicationContext: Context,
) {

    private val _state = MutableStateFlow(NotificationState())
    val state: StateFlow<NotificationState> = _state

    private val notificationManager = NotificationManagerCompat.from(applicationContext)

    private val builder = NotificationCompat.Builder(
        applicationContext,
        CHANNEL_ID,
    )


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
        val textToTranslate = state.value.originalText
        val translatedText = state.value.translatedText

        val notificationContent = """
              <div style="display: flex; align-items: center;">
                <b style="margin-left: 10px;">$textToTranslate</b>
                <br>
                <span style="margin-left: 10px;">$translatedText</span>
              </div>
           """.trimIndent()

        addListenButton(applicationContext)
        addFavouriteButton(applicationContext)

        notificationManager.notify(
            1, builder.setSmallIcon(R.mipmap.ic_launcher).setContentText(
                    Html.fromHtml(
                        notificationContent, Html.FROM_HTML_MODE_COMPACT
                    )
                ).setStyle(
                    NotificationCompat.BigTextStyle()
                        .bigText(Html.fromHtml(notificationContent, Html.FROM_HTML_MODE_COMPACT))
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
            1, builder.setContentText(
                Html.fromHtml(
                    notificationContent, Html.FROM_HTML_MODE_COMPACT
                )
            ).setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(Html.fromHtml(notificationContent, Html.FROM_HTML_MODE_COMPACT))
            ).build()
        )
    }

    private fun addFavouriteButton(
        context: Context
    ) {
        val notificationFavourite: BroadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent?) {
                saveTranslation()
                context.unregisterReceiver(this)
            }
        }

        val intentFilter = IntentFilter("com.svmsoftware.flashvocab.ACTION_FAVOURITE")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.registerReceiver(
                notificationFavourite, intentFilter, RECEIVER_EXPORTED
            )
        } else {
            context.registerReceiver(
                notificationFavourite, intentFilter
            )
        }

        val star = Intent("com.svmsoftware.flashvocab.ACTION_FAVOURITE")
        val nStar = PendingIntent.getBroadcast(
            context, 0, star, FLAG_CANCEL_CURRENT or FLAG_IMMUTABLE
        )

        builder.addAction(NotificationCompat.Action(null, "Add Bookmark", nStar))
    }

    private fun addListenButton(
        context: Context
    ) {
        val notificationFavourite: BroadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent?) {
                textToSpeechManager.shutdown()
                textToSpeechManager.speak(
                    state.value.originalText, state.value.sourceLanguageCode
                )
                context.unregisterReceiver(this)
            }
        }

        val intentFilter = IntentFilter("com.svmsoftware.flashvocab.ACTION_TTS")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.registerReceiver(
                notificationFavourite, intentFilter, RECEIVER_EXPORTED
            )
        } else {
            context.registerReceiver(
                notificationFavourite, intentFilter
            )
        }
        val star = Intent("com.svmsoftware.flashvocab.ACTION_TTS")
        val nStar = PendingIntent.getBroadcast(
            context, 0, star, FLAG_CANCEL_CURRENT or FLAG_IMMUTABLE
        )

        builder.addAction(NotificationCompat.Action(null, "Listen", nStar))
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
