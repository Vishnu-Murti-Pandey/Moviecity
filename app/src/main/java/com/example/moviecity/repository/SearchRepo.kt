package com.example.moviecity.repository

import com.example.moviecity.models.SearchResultList
import com.example.moviecity.networkRequest.MovieService
import retrofit2.Call

class SearchRepo(private val apiInstance: MovieService) {

    fun getSearchList(query: String): Call<SearchResultList> =
        apiInstance.movieInstance.searchMovie(query)

}