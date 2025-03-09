package com.example.utube.utubeapp.data.networking.dtos



data class SearchDto(
    val etag: String,
    val items: List<Item>,
    val kind: String,
    val nextPageToken: String,
    val pageInfo: PageInfo,
    val regionCode: String
)