package com.example.moviecity.repository

import androidx.lifecycle.LiveData
import com.example.moviecity.dao.UpcomingMoviesDao
import com.example.moviecity.models.UpcomingResultList
import com.example.moviecity.networkRequest.MovieService
import retrofit2.Call

class MainUpcomingRepo(
    private val upcomingDao: UpcomingMoviesDao,
    private val apiInstance: MovieService
) {

    suspend fun insert(item: UpcomingResultList) {
        upcomingDao.insert(item)
    }

    fun fetchAllUpcomingMovies(): LiveData<UpcomingResultList> {
        return upcomingDao.getUpcomingMovies()
    }

    fun getUpcomingMovie(): Call<UpcomingResultList> = apiInstance.movieInstance.getUpcomingMovie()

}