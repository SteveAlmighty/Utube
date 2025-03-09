package com.example.utube.utubeapp.data.mapper

import com.example.utube.utubeapp.data.database.VideoEntity
import com.example.utube.utubeapp.domain.model.FavoriteVideo
import com.example.utube.utubeapp.domain.model.Video

fun Video.toFavoriteVideo(): FavoriteVideo {
    return FavoriteVideo(
        id = id,
        channelTitle = snippet.channelTitle,
        publishedAt = snippet.publishedAt,
        thumbnails = snippet.thumbnails.default.url,
        title = snippet.title,
    )
}

fun Video.toVideoEntity(): VideoEntity {
    return VideoEntity(
        id = id,
        channelTitle = snippet.channelTitle,
        publishedAt = snippet.publishedAt,
        thumbnails = snippet.thumbnails.default.url,
        title = snippet.title,
    )
}

fun VideoEntity.toFavoriteVideo(): FavoriteVideo {
    return FavoriteVideo(
        id = id,
        channelTitle = channelTitle,
        publishedAt = publishedAt,
        thumbnails = thumbnails,
        title = title,
    )
}

fun FavoriteVideo.toVideoEntity(): VideoEntity {
    return VideoEntity(
        id = id,
        channelTitle = channelTitle,
        publishedAt = publishedAt,
        thumbnails = thumbnails,
        title = title,
    )
}
