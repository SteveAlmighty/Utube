package com.example.utube.utubeapp.data.repository

import com.example.utube.utubeapp.data.database.FavoriteVideoDao
import com.example.utube.utubeapp.data.mapper.toFavoriteVideo
import com.example.utube.utubeapp.data.mapper.toVideoEntity
import com.example.utube.utubeapp.data.networking.YtApi
import com.example.utube.utubeapp.data.networking.dtos.SearchDto
import com.example.utube.utubeapp.data.networking.dtov.VideoDto
import com.example.utube.utubeapp.domain.model.FavoriteVideo
import com.example.utube.utubeapp.domain.repository.YtRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.Response
import javax.inject.Inject


class YtRepositoryImpl @Inject constructor(
    private val api: YtApi,
    private val favoriteVideoDao: FavoriteVideoDao
) : YtRepository {

    override suspend fun getSearches(query: String): Response<SearchDto> {
        return api.getSearches(query)
    }

    override suspend fun getVideoById(id: String): Response<VideoDto> {
        return api.getVideoById(id)
    }

    override fun getFavoriteVideos(): Flow<List<FavoriteVideo>> {
        return favoriteVideoDao
            .getFavoriteVideos()
            .map { videoEntities ->
                videoEntities.map {it.toFavoriteVideo()}
            }
    }

    override fun isVideoFavorite(id: String): Flow<Boolean> {
        return favoriteVideoDao
            .getFavoriteVideos()
            .map { videoEntities ->
                videoEntities.any { it.id == id }
            }
    }


    override suspend fun markAsFavorite(video: FavoriteVideo) {
        favoriteVideoDao.upsert (video.toVideoEntity())
    }

    override suspend fun deleteFromFavorites(id: String) {
        favoriteVideoDao.deleteFavoriteVideo(id)
    }


}

