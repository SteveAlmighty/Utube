package com.example.utube.utubeapp.presentation.videodetail

import com.example.utube.utubeapp.domain.model.Video

data class VideoDetailState(
    val isLoading: Boolean = true,
    val isFavorite: Boolean = false,
    val video: Video? = null,
    val error: String? = null
)
