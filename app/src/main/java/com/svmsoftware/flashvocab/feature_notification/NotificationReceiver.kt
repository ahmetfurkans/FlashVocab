package com.svmsoftware.flashvocab.feature_notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.svmsoftware.flashvocab.core.domain.model.Bookmark
import com.svmsoftware.flashvocab.core.domain.repository.BookmarkRepository
import com.svmsoftware.flashvocab.core.util.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class NotificationReceiver : BroadcastReceiver() {

    @Inject
    lateinit var bookmarkRepository: BookmarkRepository

    override fun onReceive(context: Context?, intent: Intent?) {
        val action = intent?.getStringExtra("action")
        val target = intent?.getStringExtra("target")
        val source = intent?.getStringExtra("source")
        val targetLang = intent?.getStringExtra("targetLang")
        val sourceLang = intent?.getStringExtra("sourceLang")

        if (action == "textToSpeech") {
            //textToSpeech(source!!)
            println("girdim textToSpeech")
        } else {
            val bookmark = Bookmark(
                sourceText = target!!,
                targetText = source!!,
                targetLanguage = targetLang!!,
                sourceLanguage = sourceLang!!,
                time = System.currentTimeMillis(),
            )
            saveTranslation(bookmark = bookmark)
            println("girdim save")
        }
    }

    fun saveTranslation(bookmark: Bookmark) {
        CoroutineScope(Dispatchers.IO).launch {
            val result = bookmarkRepository.insertBookmark(
                bookmark
            )
            when (result) {
                is Resource.Error -> {
                }

                is Resource.Success -> {
                }
            }
        }
    }

    fun textToSpeech(text: String) {
        //TextToSpeech().invoke(text, applicationContext)
    }

}