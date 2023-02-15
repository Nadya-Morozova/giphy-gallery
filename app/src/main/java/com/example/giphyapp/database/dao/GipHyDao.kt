package com.example.giphyapp.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.giphyapp.database.data.GipHy
import com.example.giphyapp.database.data.BlockedGipHy

@Dao
interface GipHyDao {

    @Query("SELECT * FROM giphy LIMIT :limit OFFSET :offset")
    suspend fun getAllGifs(limit: Int, offset: Int): List<GipHy>

    @Query("SELECT * FROM blocklist")
    suspend fun getBlockedGifs(): List<BlockedGipHy>

}