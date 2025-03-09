package com.example.utube.utubeapp.data.networking.dtov

data class Item(
    val etag: String,
    val id: String,
    val kind: String,
    val player: Player,
    val snippet: Snippet,
    val statistics: Statistics
)