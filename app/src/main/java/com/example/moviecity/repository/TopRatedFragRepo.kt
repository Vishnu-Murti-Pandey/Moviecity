package com.example.moviecity.repository

import androidx.lifecycle.LiveData
import com.example.moviecity.dao.FragTopRatedDao
import com.example.moviecity.models.ViewAllMoviesList
import com.example.moviecity.networkRequest.MovieService
import retrofit2.Call

class TopRatedFragRepo(
    private val moviesDao: FragTopRatedDao,
    private val apiInstance: MovieService
) {

    suspend fun insert(item: ViewAllMoviesList) {
        moviesDao.insert(item)
    }

    fun fetchAllPopularMovies(): LiveData<ViewAllMoviesList> {
        return moviesDao.getMovies()
    }


    fun getAllTopRatedMovie(): Call<ViewAllMoviesList> =
        apiInstance.movieInstance.getAllTopRatedMovie()
}