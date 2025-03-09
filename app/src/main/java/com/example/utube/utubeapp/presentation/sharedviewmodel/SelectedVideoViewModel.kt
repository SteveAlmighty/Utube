package com.example.utube.utubeapp.presentation.sharedviewmodel

import androidx.lifecycle.ViewModel
import com.example.utube.utubeapp.domain.model.SearchResult
import com.example.utube.utubeapp.domain.model.Video
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class SelectedVideoViewModel: ViewModel() {

    private var _selectedVideo = MutableStateFlow<SearchResult?>(null)
    val selectedVideo = _selectedVideo.asStateFlow()

    fun onSelectVideo(video: SearchResult?) {
        _selectedVideo.value = video
    }
}