package com.example.moviecity.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moviecity.models.Movies
import com.example.moviecity.models.ViewAllMoviesList

@Dao
interface FragUpcomingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: ViewAllMoviesList)

    @Query("SELECT * FROM allMovies")
    fun getMovies(): LiveData<ViewAllMoviesList>

}