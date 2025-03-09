package com.example.utube.utubeapp.presentation.videodetail

import com.example.utube.utubeapp.domain.model.Video

sealed interface VideoDetailAction {
    data object OnBackClick: VideoDetailAction
    data object OnFavoriteClick: VideoDetailAction
    data class OnSelectedVideoChange(val video: Video): VideoDetailAction
}