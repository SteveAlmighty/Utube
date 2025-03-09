package com.example.utube.utubeapp.presentation.homescreen.components.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.utube.utubeapp.domain.model.SearchResult
import com.example.utube.utubeapp.domain.model.toSearchResult
import com.example.utube.utubeapp.domain.model.toVideo
import com.example.utube.utubeapp.domain.repository.YtRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


sealed class Result<T>(){
    data object Loading : Result<Nothing>()
    class Success<T>(val data: T) : Result<T>()
    class Error<T>(val message: String) : Result<T>()

}

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: YtRepository
): ViewModel() {


    private var cachedVids = emptyList<SearchResult>()
    private var searchJob: Job? = null
    private var observeFavoriteJob: Job? = null

    
    private val _state = MutableStateFlow(SearchState())
    val state = _state
        .onStart {
            if (cachedVids.isEmpty()) {
                observeSearchQuery()
            }
            observeFavoriteVideos()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    private val _events = Channel<SearchEvent>()
    val events = _events.receiveAsFlow()


    fun onAction(action: SearchAction) {
        when (action) {
            is SearchAction.OnVideoClick -> {
                selectVideo(action.video)
            }

            is SearchAction.OnSearchQueryChange -> {
                _state.update {
                    it.copy(searchQuery = action.query)
                }
            }

        }
    }


     private fun selectVideo(searchResult: SearchResult){
        _state.update {
            it.copy(selectedVideo = searchResult)
        }


        viewModelScope.launch {
            try {
                searchResult.id.let { it ->
                    //Log.d("vistKt", "List: searchResult = $searchResult")
                    val video = searchRepository
                        .getVideoById(it.videoId)
                    val k = video.body()?.items?.map { it.toVideo() }
//                    Log.d("vistKt", "List: searchResult = $k.")

                    _state.update {
                        it.copy(
                            theVideo = k?.get(0)
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


    @OptIn(FlowPreview::class)
    private fun observeSearchQuery() {
        state
            .map { it.searchQuery }
            .distinctUntilChanged()
            .debounce(500L)
            .onEach { query ->
                when {
                    query.isBlank() -> {
                        _state.update {
                            it.copy(
                                errorMessage = null,
                                searchResults = cachedVids
                            )
                        }
                    }

                    query.length >= 2 -> {
                        searchVideo(query)
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    private fun searchVideo(query: String){
        searchJob?.cancel()
        searchJob = viewModelScope.launch {

        _state.update {
            it.copy(
                isLoading = true
            )
        }

            try {
                val response = searchRepository.getSearches(query).body()?.items?.map { it.toSearchResult() }
                _state.update {
                    it.copy(
                        isLoading = false,
                        searchResults = response!!
                    )
                }
            } catch (e: HttpException) {
                Log.e("API", "Network error: ${e.message}")
            } catch (e: IOException) {
                Log.e("API", "Server error: ${e.message}")
            }
        }

    }

    private fun observeFavoriteVideos() {
        observeFavoriteJob?.cancel()
        observeFavoriteJob = searchRepository
            .getFavoriteVideos()
            .onEach { favoriteVideos ->
                _state.update { it.copy(
                    favoriteVideos = favoriteVideos
                ) }
            }
            .launchIn(viewModelScope)
    }
}