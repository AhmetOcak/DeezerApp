package com.database.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.database.dao.FavoriteSongsDao
import com.database.entity.FavoriteSongsEntity

@Database(
    entities = [FavoriteSongsEntity::class],
    version = 1
)
abstract class FavoriteSongsDatabase : RoomDatabase() {

    abstract fun favoriteSongsDao(): FavoriteSongsDao
}