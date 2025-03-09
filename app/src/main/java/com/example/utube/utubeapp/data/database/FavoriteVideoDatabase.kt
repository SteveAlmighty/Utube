package com.example.utube.utubeapp.data.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [VideoEntity::class],
    version = 1
)

abstract class FavoriteVideoDatabase: RoomDatabase() {

    abstract val favoriteVideoDao: FavoriteVideoDao

    companion object {
        const val DB_NAME = "video.db"
    }
}