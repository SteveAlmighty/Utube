package com.example.utube.utubeapp.data.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteVideoDao {

    @Upsert
    suspend fun upsert(book: VideoEntity)

    @Query("SELECT * FROM VideoEntity")
    fun getFavoriteVideos(): Flow<List<VideoEntity>>

    @Query("SELECT * FROM VideoEntity WHERE id = :id")
    suspend fun getFavoriteVideo(id: String): VideoEntity?

    @Query("DELETE FROM VideoEntity WHERE id = :id")
    suspend fun deleteFavoriteVideo(id: String)
}