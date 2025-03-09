package com.example.utube.utubeapp.presentation.homescreen.components.search

import com.example.utube.utubeapp.domain.model.FavoriteVideo
import com.example.utube.utubeapp.domain.model.SearchResult
import com.example.utube.utubeapp.domain.model.Video

data class SearchState(
    val searchQuery: String = "",
    val searchResults: List<SearchResult> = emptyList(),
    val selectedVideo: SearchResult? = null,
    val favoriteVideos: List<FavoriteVideo> = emptyList(),
    val theVideo: Video? = null,
    val isLoading: Boolean = true,
    val errorMessage: String? = null
)
