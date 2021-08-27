package com.example.moviecity.repository

import androidx.lifecycle.LiveData
import com.example.moviecity.dao.TopRatedMoviesDao
import com.example.moviecity.models.TopRatedResultList
import com.example.moviecity.networkRequest.MovieService
import retrofit2.Call

class MainTopRatedRepo(
    private val topRatedDao: TopRatedMoviesDao,
    private val apiInstance: MovieService
) {


    suspend fun insert(item: TopRatedResultList) {
        topRatedDao.insert(item)
    }

    fun fetchAllTopRatedMovies(): LiveData<TopRatedResultList> {
        return topRatedDao.getTopRatedMovies()
    }

    fun getTopRatedMovie(): Call<TopRatedResultList> = apiInstance.movieInstance.getTopRatedMovie()

}