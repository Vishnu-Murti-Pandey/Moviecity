package com.example.moviecity.models

import android.graphics.Bitmap
import androidx.room.*
import com.example.moviecity.dataBase.converters.ImageConverter
import com.example.moviecity.dataBase.converters.ViewAllListConverter

@Entity(tableName = "allMovies")
data class ViewAllMoviesList(

    @TypeConverters(ViewAllListConverter::class)
    @ColumnInfo(name = "results") val results: List<Movies>
){
    @PrimaryKey(autoGenerate = true)
    var id = 0
}

data class Movies(
    val id: String,
    val title: String,
    val poster_path: String,
    val vote_average: String,
    val overview: String,

    @TypeConverters(ImageConverter::class)
    var bitmap: Bitmap? = null
)

