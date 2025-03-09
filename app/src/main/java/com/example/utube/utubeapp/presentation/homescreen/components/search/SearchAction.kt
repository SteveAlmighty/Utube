package com.example.utube.utubeapp.presentation.homescreen.components.search

import com.example.utube.utubeapp.domain.model.SearchResult
import com.example.utube.utubeapp.domain.model.Video

interface SearchAction {
    data class OnSearchQueryChange(val query: String): SearchAction
    data class OnVideoClick(val video: SearchResult): SearchAction
}