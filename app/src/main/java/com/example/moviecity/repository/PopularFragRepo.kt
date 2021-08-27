package com.example.moviecity.repository

import androidx.lifecycle.LiveData
import com.example.moviecity.dao.FragPopularDao
import com.example.moviecity.models.ViewAllMoviesList
import com.example.moviecity.networkRequest.MovieService
import retrofit2.Call

class PopularFragRepo(
    private val moviesDao: FragPopularDao,
    private val apiInstance: MovieService
) {

    suspend fun insert(item: ViewAllMoviesList) {
        moviesDao.insert(item)
    }

    fun fetchAllPopularMovies(): LiveData<ViewAllMoviesList> {
        return moviesDao.getMovies()
    }

    fun getAllPopularMovie(): Call<ViewAllMoviesList> =
        apiInstance.movieInstance.getAllPopularMovie()


}