package com.example.giphyapp.network.api

import com.example.giphyapp.network.data.Data
import retrofit2.http.GET
import retrofit2.http.Query

interface GipHyApi {

    @GET("trending")
    suspend fun getTrendingGifs(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Data

    @GET("search")
    suspend fun searchGifsByQuery(
        @Query("q") query: String?,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Data

}