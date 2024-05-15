package com.svmsoftware.flashvocab.feature_notification

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NotificationActivity : AppCompatActivity() {


    @Inject
    lateinit var notificationManager: NotificationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val word = intent.getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT).toString().lowercase()
        notificationManager.performTranslationNotification(word)



        this.finish()
    }
}