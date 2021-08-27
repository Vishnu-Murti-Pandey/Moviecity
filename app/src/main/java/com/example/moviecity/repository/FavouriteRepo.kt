package com.example.moviecity.repository

import androidx.lifecycle.LiveData
import com.example.moviecity.dao.FavouriteDao
import com.example.moviecity.models.Favourite

class FavouriteRepo(private val favDao: FavouriteDao) {

    val allMovies: LiveData<List<Favourite>> = favDao.getDetailsMovies()

    suspend fun insert(item: Favourite) {
        favDao.insert(item)
    }

    suspend fun delete(item: Favourite) {
        favDao.delete(item)
    }


}