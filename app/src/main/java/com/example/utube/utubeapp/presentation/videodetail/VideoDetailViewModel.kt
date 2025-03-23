package com.example.utube.utubeapp.presentation.videodetail



import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.utube.core.navigation.Route
import com.example.utube.utubeapp.data.mapper.toFavoriteVideo
import com.example.utube.utubeapp.domain.model.toVideo
import com.example.utube.utubeapp.domain.repository.YtRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


@HiltViewModel
class VideoDetailViewModel@Inject constructor(
    private val videoRepository: YtRepository,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    private val videoId = savedStateHandle.toRoute<Route.VideoDetailScreen>().id

    private val _state = MutableStateFlow(VideoDetailState())
    val state = _state
        .onStart {
            fetchVideoDescription()
            observeFavoriteStatus()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    fun onAction(action: VideoDetailAction) {
        when(action) {
            is VideoDetailAction.OnSelectedVideoChange -> {
                _state.update { it.copy(
                    video = action.video
                ) }
            }
            is VideoDetailAction.OnFavoriteClick -> {
                viewModelScope.launch {
                    if(state.value.isFavorite) {
                        videoRepository.deleteFromFavorites(videoId)
                    } else {
                        state.value.video?.let { video ->
                            videoRepository.markAsFavorite(video.toFavoriteVideo())
                        }
                    }
                    _state.update { it.copy(isFavorite = !it.isFavorite)}
                }
            }
            else -> Unit
        }
    }

    private fun observeFavoriteStatus() {
        _state.update { it.copy(
            isFavorite = it.isFavorite
        ) }
        videoRepository
            .isVideoFavorite(videoId)
            .onEach { isFavorite ->
                _state.update { it.copy(
                    isFavorite = isFavorite
                ) }
            }
            .launchIn(viewModelScope)
    }

    private fun fetchVideoDescription() {
        viewModelScope.launch {
            try {
                videoId.let { it ->
                    //Log.d("vistKt", "List: searchResult = $searchResult")
                    val video = videoRepository
                        .getVideoById(it)
                    val k = video.body()?.items?.map { it.toVideo() }
//                    Log.d("vistKt", "List: searchResult = $k.")

                    _state.update {
                        it.copy(
                            video = k?.get(0)
                        )
                    }
                }
            } catch (
                e: HttpException
            ) {
                Log.e("API", "Network error: ${e.message}")
            } catch (
                e: IOException
            ) {
                Log.e("API", "Server error: ${e.message}")

            }
        }






    }
}