package com.example.giphyapp.utils

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.giphyapp.data.GlobalGipHy
import com.example.giphyapp.database.repositories.GipHyDatabaseRepository
import com.example.giphyapp.network.paging.GipHyPagingDatabaseSource
import com.example.giphyapp.network.paging.GipHyPagingSource
import com.example.giphyapp.network.repositories.GipHyRepository
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class GipHyPagingSourceFactory @Inject constructor(
    private val gipHyRepository: GipHyRepository,
    private val gipHyDatabaseRepository: GipHyDatabaseRepository,
    private val connectivityTracker: ConnectivityTracker
) {

    fun createFlow(
        query: String?
    ): Flow<PagingData<GlobalGipHy>> {
        val pagingSource = if (!connectivityTracker.isNetworkConnected()) {
            GipHyPagingDatabaseSource(gipHyDatabaseRepository)
        } else {
            GipHyPagingSource(gipHyRepository, gipHyDatabaseRepository, query)
        }

        return Pager(PagingConfig(6)) { pagingSource }.flow
    }

}