package com.example.moviecity.networkRequest

import com.example.moviecity.models.*
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

// Popular movies -> https://api.themoviedb.org/3/movie/popular?api_key=363c3616e638a9d6456a77026c2d1295
// Upcoming movies -> https://api.themoviedb.org/3/movie/upcoming?api_key=363c3616e638a9d6456a77026c2d1295
// Top Rated movies -> https://api.themoviedb.org/3/movie/top_rated?api_key=363c3616e638a9d6456a77026c2d1295
// Search movies -> https://api.themoviedb.org/3/search/movie?api_key=363c3616e638a9d6456a77026c2d1295&query=Jack+Reacher
// Details movies -> https://api.themoviedb.org/3/movie/497698?api_key=363c3616e638a9d6456a77026c2d1295&append_to_response=videos,images,credits


const val BASE_URL = "https://api.themoviedb.org/"
const val API_KEY = "363c3616e638a9d6456a77026c2d1295"

interface MovieInterface {

    @GET("3/movie/popular?api_key=$API_KEY")
    fun getPopularMovie(): Call<PopularResultList>

    @GET("3/movie/upcoming?api_key=$API_KEY")
    fun getUpcomingMovie(): Call<UpcomingResultList>

    @GET("3/movie/top_rated?api_key=$API_KEY")
    fun getTopRatedMovie(): Call<TopRatedResultList>

    @GET("3/movie/popular?api_key=$API_KEY")
    fun getAllPopularMovie(): Call<ViewAllMoviesList>

    @GET("3/movie/upcoming?api_key=$API_KEY")
    fun getAllUpcomingMovie(): Call<ViewAllMoviesList>

    @GET("3/movie/top_rated?api_key=$API_KEY")
    fun getAllTopRatedMovie(): Call<ViewAllMoviesList>

    @GET("3/search/movie?api_key=$API_KEY")
    fun searchMovie(@Query("query")movie: String): Call<SearchResultList>

    @GET("3/movie/{movieId}?api_key=$API_KEY&append_to_response=videos,images,credits")
    fun getMovieDetails(
        @Path("movieId")id: String?): Call<MovieDetails>

}

object MovieService {
    val movieInstance: MovieInterface

    init {
        val retrofit: Retrofit by lazy {
            Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        }

        movieInstance = retrofit.create(MovieInterface::class.java)
    }

}