package com.example.giphyapp.network.repositories

import com.example.giphyapp.network.api.GipHyApi
import com.example.giphyapp.network.data.Data
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject


class GipHyRepository @Inject constructor(
    private val gipHyApi: GipHyApi
) {

    suspend fun getTrendingGifs(limit: Int, offset: Int): Data = withContext(Dispatchers.IO) {
        if (offset >= limit) {
            delay(3000L)
        }
        gipHyApi.getTrendingGifs(limit, offset)
    }

    suspend fun searchGifsByQuery(query: String, limit: Int, offset: Int) =
        withContext(Dispatchers.IO) {
            if (offset >= limit) {
                delay(3000L)
            }
            gipHyApi.searchGifsByQuery(query, limit, offset)
        }

}