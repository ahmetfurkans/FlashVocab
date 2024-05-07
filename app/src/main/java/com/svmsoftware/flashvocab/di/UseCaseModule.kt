package com.svmsoftware.flashvocab.di

import android.content.Context
import com.svmsoftware.flashvocab.core.domain.use_cases.TextToSpeech
import com.svmsoftware.flashvocab.core.domain.use_cases.TextToSpeechManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideTextToSpeechUseCase(
        @ApplicationContext context: Context
    ): TextToSpeech {
        return TextToSpeech(context)
    }

    @Provides
    @Singleton
    fun provideTextToSpeechManager(
        @ApplicationContext context: Context
    ): TextToSpeechManager {
        return TextToSpeechManager(context)
    }
}