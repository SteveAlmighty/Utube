package com.example.utube.utubeapp.data.networking.dtov

data class VideoDto(
    val etag: String,
    val items: List<Item>,
    val kind: String,
    val pageInfo: PageInfo
)