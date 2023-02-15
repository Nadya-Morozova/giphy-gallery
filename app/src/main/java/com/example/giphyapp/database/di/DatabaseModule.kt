package com.example.giphyapp.database.di

import android.content.Context
import androidx.room.Room
import com.example.giphyapp.database.GipHyDatabase
import com.example.giphyapp.database.dao.BlocklistDao
import com.example.giphyapp.database.dao.GipHyDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    private const val DATABASE_NAME = "GipHy"

    @Provides
    fun provideDatabase(@ApplicationContext context: Context): GipHyDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            GipHyDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    @Provides
    fun provideGipHyDao(gipHyDatabase: GipHyDatabase): GipHyDao {
        return gipHyDatabase.gipHyDao()
    }

    @Provides
    fun provideBlockListDao(gipHyDatabase: GipHyDatabase): BlocklistDao {
        return gipHyDatabase.blocklistDao()
    }

}