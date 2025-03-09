package com.example.utube.utubeapp.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class VideoEntity(
    @PrimaryKey(autoGenerate = false) val id: String,
    val channelTitle: String,
    val publishedAt: String,
    val thumbnails: String,
    val title: String
)