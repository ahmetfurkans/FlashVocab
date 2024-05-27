package com.svmsoftware.flashvocab.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.svmsoftware.flashvocab.core.data.local.DailyResetDatabase
import com.svmsoftware.flashvocab.core.data.repository.DailyResetRepositoryImpl
import com.svmsoftware.flashvocab.core.domain.repository.DailyResetRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DailyResetModule {

    @Provides
    @Singleton
    fun provideDailyResetDatabase(app: Application): DailyResetDatabase {
        return Room.databaseBuilder(
            app, DailyResetDatabase::class.java, DailyResetDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideDailyResetRepository(
        db: DailyResetDatabase,
        @ApplicationContext context: Context
    ): DailyResetRepository {
        return DailyResetRepositoryImpl(db, context)
    }
}