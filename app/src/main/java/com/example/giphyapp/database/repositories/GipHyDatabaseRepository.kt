package com.example.giphyapp.database.repositories

import com.example.giphyapp.data.GlobalGipHy
import com.example.giphyapp.database.dao.BlocklistDao
import com.example.giphyapp.database.dao.GipHyDao
import com.example.giphyapp.database.data.BlockedGipHy
import com.example.giphyapp.database.data.GipHy
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class GipHyDatabaseRepository @Inject constructor(
    private val gipHyDao: GipHyDao,
    private val blocklistDao: BlocklistDao
) {

    suspend fun getAllGifs(limit: Int, offset: Int) = gipHyDao.getAllGifs(limit, offset)

    suspend fun insertGipHy(globalGipHy: GlobalGipHy) {
        val gipHy = GipHy(
            globalGipHy.id ?: return,
            globalGipHy.title ?: return,
            globalGipHy.url ?: return
        )
        gipHyDao.insertGipHy(gipHy)
    }

    suspend fun getBlockedGifs() =
        blocklistDao.getBlockedGifs()

    suspend fun insertToBlockList(blockedGipHy: BlockedGipHy) =
        blocklistDao.insertToBlockList(blockedGipHy)

}