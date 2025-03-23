package com.example.utube.utubeapp.presentation.homescreen.components.search

import com.example.utube.utubeapp.domain.model.FavoriteVideo
import com.example.utube.utubeapp.domain.model.SearchResult

interface SearchAction {
    data class OnSearchQueryChange(val query: String): SearchAction
    data class OnVideoClick(val video: SearchResult): SearchAction
    data class OnFavouriteVideoClick(val video: FavoriteVideo): SearchAction

}