package com.svmsoftware.flashvocab.feature_notification

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class NotificationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val word = intent.getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT).toString().lowercase()

        val serviceIntent = Intent(applicationContext, NotificationService::class.java).apply {
            putExtra("word", word)
        }
        ContextCompat.startForegroundService(applicationContext, serviceIntent)

        this.finish()
    }
}