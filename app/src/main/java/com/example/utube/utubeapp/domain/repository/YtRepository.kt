package com.example.utube.utubeapp.domain.repository

import com.example.utube.core.domain.util.DataError
import com.example.utube.core.domain.util.EmptyResult
import com.example.utube.utubeapp.data.database.VideoEntity
import com.example.utube.utubeapp.data.networking.dtos.SearchDto
import com.example.utube.utubeapp.data.networking.dtov.VideoDto
import com.example.utube.utubeapp.domain.model.FavoriteVideo
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface YtRepository {

    suspend fun getSearches(query: String): Response<SearchDto>

    suspend fun getVideoById(id: String): Response<VideoDto>


    fun getFavoriteVideos(): Flow<List<FavoriteVideo>>
    fun isVideoFavorite(id: String): Flow<Boolean>
    suspend fun markAsFavorite(video: FavoriteVideo)
    suspend fun deleteFromFavorites(id: String)

}