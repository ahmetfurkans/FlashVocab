package com.svmsoftware.flashvocab.feature_notification

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.content.Context
import android.content.Intent
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
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class NotificationManager @Inject constructor(
    private val settingRepository: SettingRepository,
    private val notificationBuilder: NotificationCompat.Builder,
    private val notificationManager: NotificationManagerCompat,
    @ApplicationContext private val context: Context
) {

    private val _state = MutableStateFlow(NotificationState())
    val state: StateFlow<NotificationState> = _state

    val snoozeIntent = Intent(context, MainActivity::class.java).apply {

    }

    private var getSettingsJob: Job? = null

    val snoozePendingIntent: PendingIntent =
        PendingIntent.getBroadcast(context, 0, snoozeIntent, FLAG_IMMUTABLE)

    fun

    fun processTranslate(word: String?) {
        if (word.isNullOrEmpty()) {
            showNotification("Something went wrong")
        } else {
            val result = ProcessTranslate().invoke(
                source = word,
                targetLang = state.value.userSettings?.translatedLangCode!!
            )
            when (result) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        target = result.data!!
                    )
                    showNotification(word)
                }

                is Resource.Error -> {
                    showNotification(word)
                }
            }
        }
    }


    @SuppressLint("MissingPermission")
    private fun showNotification(source: String) {
        val text1 = source
        val text2 = state.value.target

        val html = """
           <div style="display: flex; align-items: center;">
             <b style="margin-left: 10px;">$text1</b>
             <br>
             <span style="margin-left: 10px;">$text2</span>
           </div>
        """.trimIndent()

        notificationManager.notify(
            1, notificationBuilder
                .setStyle(NotificationCompat.BigTextStyle()
                    .bigText(Html.fromHtml(html, Html.FROM_HTML_MODE_COMPACT)))
                .addAction(
                    R.drawable.turkish, "Save",
                    snoozePendingIntent
                )
                .addAction(
                    R.drawable.turkish, "Read Source Text",
                    snoozePendingIntent
                )
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build()
        )
    }

    fun getSettings(word: String?) {
        GlobalScope.launch {
            settingRepository.getSettings().collect {
                if (it == null) {
                    settingRepository.insertSetting(UserSettings())
                } else {
                    _state.value = _state.value.copy(
                        userSettings = it
                    )
                    processTranslate(word)
                }
            }
        }
    }
}
