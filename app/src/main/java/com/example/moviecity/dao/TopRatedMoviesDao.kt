package com.example.moviecity.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moviecity.models.TopRatedMovies
import com.example.moviecity.models.TopRatedResultList

@Dao
interface TopRatedMoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: TopRatedResultList)

    @Query("SELECT * FROM topRatedMovies")
    fun getTopRatedMovies(): LiveData<TopRatedResultList>

}