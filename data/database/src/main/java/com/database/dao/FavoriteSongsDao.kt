package com.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.database.entity.FavoriteSongsEntity

@Dao
interface FavoriteSongsDao {

    @Insert
    suspend fun addFavoriteSong(favoriteSongsEntity: FavoriteSongsEntity)

    @Query("SELECT * FROM favorite_song")
    suspend fun getAllFavoriteSongs(): List<FavoriteSongsEntity>

    @Query("DELETE FROM favorite_song WHERE id = :songId")
    suspend fun removeFavoriteSong(songId: Long)
}