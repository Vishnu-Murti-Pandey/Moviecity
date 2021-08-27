package com.example.moviecity.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favouriteMovies")
data class Favourite(

    @ColumnInfo(name = "movieId") val movieId: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "isFav") val isFav: Boolean,
    @ColumnInfo(name = "poster") val poster_path: String,
    @ColumnInfo(name = "ratings") val vote_average: String,
    @ColumnInfo(name = "overview") val overview: String,

//    @TypeConverters(ImageConverter::class)
//    var bitmap: Bitmap? = null

) {
    @PrimaryKey(autoGenerate = true)
    var id = 0
}