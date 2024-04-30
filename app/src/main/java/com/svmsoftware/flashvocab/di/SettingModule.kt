package com.svmsoftware.flashvocab.di

import android.app.Application
import androidx.room.Room
import com.svmsoftware.flashvocab.core.data.local.SettingDatabase
import com.svmsoftware.flashvocab.core.data.repository.SettingRepositoryImpl
import com.svmsoftware.flashvocab.core.domain.repository.SettingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SettingModule {

    @Provides
    @Singleton
    fun provideSettingDatabase(app: Application): SettingDatabase {
        return Room.databaseBuilder(
            app, SettingDatabase::class.java, SettingDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideSettingRepository(
        db: SettingDatabase
    ): SettingRepository {
        return SettingRepositoryImpl(db.dao)
    }
}