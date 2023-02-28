package com.example.giphyapp.screens.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.giphyapp.data.GlobalGipHy
import com.example.giphyapp.database.data.BlockedGipHy
import com.example.giphyapp.database.repositories.GipHyDatabaseRepository
import com.example.giphyapp.network.paging.GipHyPagingDatabaseSource
import com.example.giphyapp.network.paging.GipHyPagingSource
import com.example.giphyapp.network.repositories.GipHyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val gipHyRepository: GipHyRepository,
    private val gipHyDatabaseRepository: GipHyDatabaseRepository
) : ViewModel() {

    private var pagingSource: PagingSource<Int, GlobalGipHy> =
        GipHyPagingSource(gipHyRepository, gipHyDatabaseRepository, null)

    var flow = Pager(PagingConfig(6)) {
        pagingSource
    }.flow.cachedIn(viewModelScope)

    fun setSearchQuery(queryText: String?, fromDatabase: Boolean) {
        pagingSource = if (fromDatabase) {
            GipHyPagingDatabaseSource(gipHyDatabaseRepository)
        } else {
            GipHyPagingSource(gipHyRepository, gipHyDatabaseRepository, queryText)
        }

        flow = Pager(PagingConfig(6)) { pagingSource }.flow.cachedIn(
            viewModelScope
        )

    }

    fun insertGifsToBlockList(blockedGipHy: BlockedGipHy) {
        viewModelScope.launch {
            gipHyDatabaseRepository.insertToBlockList(blockedGipHy)
        }
    }

}

