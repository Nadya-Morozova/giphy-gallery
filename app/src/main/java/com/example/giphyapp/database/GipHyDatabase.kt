package com.example.giphyapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.giphyapp.database.dao.BlocklistDao
import com.example.giphyapp.database.dao.GipHyDao
import com.example.giphyapp.database.data.GipHy
import com.example.giphyapp.database.data.BlockedGipHy

@Database(entities = [GipHy::class, BlockedGipHy::class], version = 1)
abstract class GipHyDatabase: RoomDatabase() {
    abstract fun gipHyDao(): GipHyDao

    abstract fun blocklistDao(): BlocklistDao
}