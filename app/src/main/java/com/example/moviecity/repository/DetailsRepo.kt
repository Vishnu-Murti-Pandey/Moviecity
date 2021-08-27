package com.example.moviecity.repository

import androidx.lifecycle.LiveData
import com.example.moviecity.dao.DetailsDao
import com.example.moviecity.models.MovieDetails
import com.example.moviecity.networkRequest.MovieService
import retrofit2.Call

class DetailsRepo(private val detailsDao: DetailsDao, private val apiInstance: MovieService) {

    suspend fun insert(item: MovieDetails) {
        detailsDao.insert(item)
    }

    fun fetchAllPopularMovies(): LiveData<MovieDetails> {
        return detailsDao.getDetailsMovies()
    }


    fun getDetailsRepo(id: String?): Call<MovieDetails> =
        apiInstance.movieInstance.getMovieDetails(id)


}