package com.example.moviecity.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moviecity.models.MovieDetails

@Dao
interface DetailsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: MovieDetails)

    @Query("SELECT * FROM detailsTable")
    fun getDetailsMovies(): LiveData<MovieDetails>

}