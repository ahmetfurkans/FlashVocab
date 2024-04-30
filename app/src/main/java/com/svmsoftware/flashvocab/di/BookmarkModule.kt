package com.svmsoftware.flashvocab.di

import android.app.Application
import androidx.room.Room
import com.svmsoftware.flashvocab.core.data.local.BookmarkDatabase
import com.svmsoftware.flashvocab.core.data.repository.BookmarkRepositoryImpl
import com.svmsoftware.flashvocab.core.domain.repository.BookmarkRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BookmarkModule {

    @Provides
    @Singleton
    fun provideBookmarkDatabase(app: Application): BookmarkDatabase {
        return Room.databaseBuilder(
            app, BookmarkDatabase::class.java, BookmarkDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideBookmarkRepository(
        db: BookmarkDatabase
    ): BookmarkRepository {
        return BookmarkRepositoryImpl(db.dao)
    }
}