package com.example.moviecity.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.moviecity.models.Favourite

@Dao
interface FavouriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: Favourite)

    @Delete
    suspend fun delete(item: Favourite)

    @Query("SELECT * FROM favouriteMovies")
    fun getDetailsMovies(): LiveData<List<Favourite>>

}