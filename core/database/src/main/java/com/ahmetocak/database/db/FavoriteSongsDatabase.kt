package com.ahmetocak.database.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ahmetocak.database.dao.FavoriteSongsDao
import com.ahmetocak.database.entity.FavoriteSongsEntity

@Database(
    entities = [FavoriteSongsEntity::class],
    version = 1
)
abstract class FavoriteSongsDatabase : RoomDatabase() {

    abstract fun favoriteSongsDao(): FavoriteSongsDao
}