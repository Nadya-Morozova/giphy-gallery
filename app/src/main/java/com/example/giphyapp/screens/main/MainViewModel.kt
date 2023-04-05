package com.example.giphyapp.screens.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.cachedIn
import com.example.giphyapp.data.GlobalGipHy
import com.example.giphyapp.database.data.BlockedGipHy
import com.example.giphyapp.database.repositories.GipHyDatabaseRepository
import com.example.giphyapp.network.paging.GipHyPagingSource
import com.example.giphyapp.network.repositories.GipHyRepository
import com.example.giphyapp.utils.GipHyPagingSourceFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val gipHyDatabaseRepository: GipHyDatabaseRepository,
    private val gipHyPagingSourceFactory: GipHyPagingSourceFactory,
) : ViewModel() {

    private val queryLiveData = MutableLiveData<String?>(null)

    private lateinit var listOfGifsFlow: Flow<PagingData<GlobalGipHy>>

    private val _giphyPagingData = MutableLiveData<PagingData<GlobalGipHy>>()
    val giphyPagingData: LiveData<PagingData<GlobalGipHy>> = _giphyPagingData

    init {
        queryLiveData.observeForever {
            listOfGifsFlow = gipHyPagingSourceFactory.createFlow(it).cachedIn(viewModelScope)

            viewModelScope.launch {
                listOfGifsFlow.collectLatest { pagingData ->
                    _giphyPagingData.value = pagingData
                }
            }
        }
    }

    fun setSearchQuery(queryText: String?) {
        queryLiveData.value = queryText
    }

    fun insertGifsToBlockList(blockedGipHy: BlockedGipHy) {
        viewModelScope.launch {
            gipHyDatabaseRepository.insertToBlockList(blockedGipHy)
        }
    }

}

