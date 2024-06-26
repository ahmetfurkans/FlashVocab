package com.svmsoftware.flashvocab.feature_notification

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_CANCEL_CURRENT
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.RECEIVER_EXPORTED
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.text.Html
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.svmsoftware.flashvocab.R
import com.svmsoftware.flashvocab.core.domain.model.Bookmark
import com.svmsoftware.flashvocab.core.domain.model.DailyReset
import com.svmsoftware.flashvocab.core.domain.repository.BookmarkRepository
import com.svmsoftware.flashvocab.core.domain.repository.DailyResetRepository
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
import kotlin.random.Random

class NotificationManager @Inject constructor(
    private val settingRepository: SettingRepository,
    private val bookmarkRepository: BookmarkRepository,
    private val dailyResetRepository: DailyResetRepository,
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

    private var notificationFavourite: BroadcastReceiver? = null
    private var notificationListen: BroadcastReceiver? = null

    init {
        getDailyResetNotificationCount()
    }


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
        if (state.value.dailyReset.dailyNotificationCount >= 10) {
            showTranslationWarningNotification("Your daily notification limit has been reached for today.")
        } else {
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
                        insertDailyResetNotification(null)
                        showTranslationSuccessNotification()
                    }

                    is Resource.Error -> {
                        showTranslationWarningNotification(result.desc!!)
                    }
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
            Random.nextInt(1000), builder.setSmallIcon(R.mipmap.ic_launcher).setContentText(
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
        notificationFavourite = object : BroadcastReceiver() {
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
        notificationListen = object : BroadcastReceiver() {
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
                notificationListen, intentFilter, RECEIVER_EXPORTED
            )
        } else {
            context.registerReceiver(
                notificationListen, intentFilter
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

    private fun getDailyResetNotificationCount() {
        CoroutineScope(Dispatchers.IO).launch {
            dailyResetRepository.getDailyReset().collect {
                if (it == null) {
                    insertDailyResetNotification(DailyReset())
                } else {
                    _state.value = _state.value.copy(
                        dailyReset = it
                    )
                }
            }
        }
    }

    private fun insertDailyResetNotification(dailyResetArg: DailyReset?) {
        CoroutineScope(Dispatchers.IO).launch {
            val dailyReset = state.value.dailyReset.copy(
                dailyNotificationCount = state.value.dailyReset.dailyNotificationCount + 1
            )
            dailyResetRepository.insertDailyReset(dailyReset = dailyResetArg ?: dailyReset)
        }
    }
}
