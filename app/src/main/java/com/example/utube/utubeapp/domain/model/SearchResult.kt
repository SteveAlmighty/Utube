package com.example.utube.utubeapp.domain.model

import com.example.utube.utubeapp.data.networking.dtos.Id
import com.example.utube.utubeapp.data.networking.dtos.Item
import com.example.utube.utubeapp.data.networking.dtos.Snippet


data class SearchResult(
    val etag: String,
    val id: Id,
    val kind: String,
    val snippet: Snippet
)


fun Item.toSearchResult(): SearchResult {
    return SearchResult(
        kind = kind,
        etag = etag,
        id = id,
        snippet = snippet
    )
}
