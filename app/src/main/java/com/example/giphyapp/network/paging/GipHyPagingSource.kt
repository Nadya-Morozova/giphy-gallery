package com.example.giphyapp.network.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.giphyapp.data.GlobalGipHy
import com.example.giphyapp.database.data.BlockedGipHy
import com.example.giphyapp.database.repositories.GipHyDatabaseRepository
import com.example.giphyapp.network.data.Response
import com.example.giphyapp.network.repositories.GipHyRepository
import kotlinx.coroutines.delay

class GipHyPagingSource(
    private val gipHyRepository: GipHyRepository,
    private val gipHyDatabaseRepository: GipHyDatabaseRepository,
    private val query: String? = null
) : PagingSource<Int, GlobalGipHy>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GlobalGipHy> {
        val position = params.key ?: 1
        val offset = if (params.key != null) position + 6 else 1
        return try {
            val blocklist = gipHyDatabaseRepository.getBlockedGifs()

            val gifs = if (query == null) {
                gipHyRepository.getTrendingGifs(6, offset).data
            } else {
                gipHyRepository.searchGifsByQuery(query = query, 6, offset = offset).data
            }

            val listOfGlobalGifs = convertGipHyToGlobal(gifs)

            if (query == null) {
                listOfGlobalGifs.forEach {
                    gipHyDatabaseRepository.insertGipHy(it)
                }
            }

            LoadResult.Page(
                data = filteredListGifs(listOfGlobalGifs, blocklist),
                prevKey = null,
                nextKey = offset
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, GlobalGipHy>): Int? {
        return null
    }

    private fun convertGipHyToGlobal(gifs: List<Response>): List<GlobalGipHy> {
        return gifs.map { response ->
            GlobalGipHy(
                id = response.id,
                title = response.title,
                url = response.images.original.url
            )
        }
    }

    private fun filteredListGifs(
        listOfGifs: List<GlobalGipHy>,
        blocklist: List<BlockedGipHy>
    ): List<GlobalGipHy> {
        var filteredListOfGifs = mutableListOf<GlobalGipHy>()
        if (blocklist.isNotEmpty()) {
            for (gif in listOfGifs) {
                var removeItem = false

                for (blockItem in blocklist) {
                    if (gif.id == blockItem.idGif) {
                        removeItem = true
                        break
                    }
                }

                if (!removeItem)
                    filteredListOfGifs.add(gif)
            }
        } else {
            filteredListOfGifs = listOfGifs.toMutableList()
        }
        return filteredListOfGifs
    }

}

class GipHyPagingDatabaseSource(
    private val gipHyDatabaseRepository: GipHyDatabaseRepository
) : PagingSource<Int, GlobalGipHy>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GlobalGipHy> {
        val page = params.key ?: 0
        return try {
            val entities = gipHyDatabaseRepository.getAllGifs(
                params.loadSize,
                page * params.loadSize
            )

            if (page != 0) delay(2000L)

            val newList = entities.map { response ->
                GlobalGipHy(
                    id = response.idGif,
                    title = response.title,
                    url = response.url
                )
            }

            LoadResult.Page(
                data = newList,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (entities.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, GlobalGipHy>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

}