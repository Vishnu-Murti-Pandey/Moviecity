package com.example.moviecity.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moviecity.models.UpcomingResultList

@Dao
interface UpcomingMoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: UpcomingResultList)

    @Query("SELECT * FROM upcomingMovies")
    fun getUpcomingMovies(): LiveData<UpcomingResultList>

}