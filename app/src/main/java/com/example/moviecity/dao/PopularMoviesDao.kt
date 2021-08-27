package com.example.moviecity.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moviecity.models.PopularMovies
import com.example.moviecity.models.PopularResultList

@Dao
interface PopularMoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert (item: PopularResultList)

    @Query("SELECT * FROM popularMovies")
    fun getPopularMovies(): LiveData<PopularResultList>

}