package com.example.moviecity.repository

import androidx.lifecycle.LiveData
import com.example.moviecity.dao.PopularMoviesDao
import com.example.moviecity.models.PopularResultList
import com.example.moviecity.networkRequest.MovieService
import retrofit2.Call

class MainPopularRepo(
    private val popularDao: PopularMoviesDao,
    private val apiInstance: MovieService
) {


    suspend fun insert(item: PopularResultList) {
        popularDao.insert(item)
    }

    fun fetchAllPopularMovies(): LiveData<PopularResultList> {
        return popularDao.getPopularMovies()
    }

    fun getPopularMovie(): Call<PopularResultList> = apiInstance.movieInstance.getPopularMovie()


}
