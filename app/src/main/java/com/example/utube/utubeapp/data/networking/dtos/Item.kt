package com.example.utube.utubeapp.data.networking.dtos

data class Item(
    val etag: String,
    val id: Id,
    val kind: String,
    val snippet: Snippet
)