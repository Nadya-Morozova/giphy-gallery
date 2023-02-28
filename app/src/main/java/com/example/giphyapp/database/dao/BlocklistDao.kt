package com.example.giphyapp.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.giphyapp.database.data.BlockedGipHy
import com.example.giphyapp.database.data.GipHy

@Dao
interface BlocklistDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertToBlockList(blockedGipHy: BlockedGipHy)

    @Query("SELECT * FROM blocklist")
    suspend fun getBlockedGifs(): List<BlockedGipHy>

}