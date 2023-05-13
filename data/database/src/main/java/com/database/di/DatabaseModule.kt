package com.database.di

import android.content.Context
import androidx.room.Room
import com.database.db.FavoriteSongsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideFavoriteSongsDb(@ApplicationContext context: Context): FavoriteSongsDatabase {
        return Room.databaseBuilder(
            context,
            FavoriteSongsDatabase::class.java,
            "app_database"
        ).build()
    }
}