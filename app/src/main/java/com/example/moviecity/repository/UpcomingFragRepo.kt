package com.example.moviecity.repository

import androidx.lifecycle.LiveData
import com.example.moviecity.dao.FragUpcomingDao
import com.example.moviecity.models.ViewAllMoviesList
import com.example.moviecity.networkRequest.MovieService
import retrofit2.Call

class UpcomingFragRepo(
    private val moviesDao: FragUpcomingDao,
    private val apiInstance: MovieService
) {

    suspend fun insert(item: ViewAllMoviesList) {
        moviesDao.insert(item)
    }

    fun fetchAllPopularMovies(): LiveData<ViewAllMoviesList> {
        return moviesDao.getMovies()
    }

    fun getAllUpcomingMovie(): Call<ViewAllMoviesList> =
        apiInstance.movieInstance.getAllUpcomingMovie()

}