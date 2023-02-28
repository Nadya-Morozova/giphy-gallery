package com.example.giphyapp.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.cachedIn
import com.example.giphyapp.data.GlobalGipHy
import com.example.giphyapp.database.data.BlockedGipHy
import com.example.giphyapp.database.repositories.GipHyDatabaseRepository
import com.example.giphyapp.network.paging.GipHyPagingDatabaseSource
import com.example.giphyapp.network.paging.GipHyPagingSource
import com.example.giphyapp.network.repositories.GipHyRepository
import com.example.giphyapp.utils.ConnectivityTracker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val gipHyRepository: GipHyRepository,
    private val gipHyDatabaseRepository: GipHyDatabaseRepository,
    private val connectivityTracker: ConnectivityTracker,
) : ViewModel() {

    private var pagingSource: PagingSource<Int, GlobalGipHy> =
        GipHyPagingSource(gipHyRepository, gipHyDatabaseRepository, null)

    var listOfGifsFlow = Pager(PagingConfig(6)) {
        pagingSource
    }.flow.cachedIn(viewModelScope)

    fun setSearchQuery(queryText: String?) {
        pagingSource = if (!connectivityTracker.isNetworkConnected()) {
            GipHyPagingDatabaseSource(gipHyDatabaseRepository)
        } else {
            GipHyPagingSource(gipHyRepository, gipHyDatabaseRepository, queryText)
        }

        listOfGifsFlow = Pager(PagingConfig(6)) { pagingSource }.flow.cachedIn(
            viewModelScope
        )

    }

    fun insertGifsToBlockList(blockedGipHy: BlockedGipHy) {
        viewModelScope.launch {
            gipHyDatabaseRepository.insertToBlockList(blockedGipHy)
        }
    }

}

