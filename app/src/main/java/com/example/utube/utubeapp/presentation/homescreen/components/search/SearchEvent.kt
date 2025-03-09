package com.example.utube.utubeapp.presentation.homescreen.components.search

import com.example.utube.core.domain.util.NetworkError

interface SearchEvent {
    data class Error(val error: NetworkError): SearchEvent
}