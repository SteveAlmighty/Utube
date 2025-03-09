package com.example.utube.utubeapp.domain.model

import com.example.utube.utubeapp.data.networking.dtov.Item
import com.example.utube.utubeapp.data.networking.dtov.Player
import com.example.utube.utubeapp.data.networking.dtov.Snippet
import com.example.utube.utubeapp.data.networking.dtov.Statistics


data class Video(
    val etag: String,
    val id: String,
    val kind: String,
    val player: Player,
    val snippet: Snippet,
    val statistics: Statistics
)

fun Item.toVideo(): Video {
    return Video(
        kind = kind,
        etag = etag,
        id = id,
        player = player,
        snippet = snippet,
        statistics = statistics,
    )
}