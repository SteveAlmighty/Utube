package com.example.utube.core.navigation

import kotlinx.serialization.Serializable

sealed interface Route {

    @Serializable
    data object UtubeGraph: Route

    @Serializable
    data object LoginScreen: Route

    @Serializable
    data object SignUpScreen: Route

    @Serializable
    data object HomeScreen: Route

    @Serializable
    data object SearchScreenRoot: Route

    @Serializable
    data class VideoDetailScreen(val id: String): Route
}