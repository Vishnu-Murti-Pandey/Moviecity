package com.example.moviecity.models

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.moviecity.dataBase.converters.ImageConverter
import com.example.moviecity.dataBase.converters.PopularListConverter


@Entity(tableName = "popularMovies")
data class PopularResultList(

    @TypeConverters(PopularListConverter::class)
    @ColumnInfo(name = "results") val results: List<PopularMovies>
) {
    @PrimaryKey(autoGenerate = true)
    var id = 0
}

data class PopularMovies(
    val id: String,
    val title: String,
    val poster_path: String,
    val vote_average: String,

    @TypeConverters(ImageConverter::class)
    var bitmap: Bitmap? = null
)
