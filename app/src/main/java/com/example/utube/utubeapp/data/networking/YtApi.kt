package com.example.utube.utubeapp.data.networking

import com.example.utube.utubeapp.data.networking.dtos.SearchDto
import com.example.utube.utubeapp.data.networking.dtov.VideoDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface YtApi {

    @GET("search?part=snippet&maxResults=25&q=&key=AIzaSyApqQqdy3w4SNULx7PFCNqjOZM_8l4BeHw")
    suspend fun getSearches(@Query("q") query: String): Response<SearchDto>

    @GET("videos?part=snippet,player,statistics&id=&key=AIzaSyApqQqdy3w4SNULx7PFCNqjOZM_8l4BeHw")
    suspend fun getVideoById(@Query("id") id: String): Response<VideoDto>

}