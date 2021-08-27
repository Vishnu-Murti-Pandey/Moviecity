package com.example.moviecity.models

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.moviecity.dataBase.converters.ImageConverter
import com.example.moviecity.dataBase.converters.UpcomingListConverter


@Entity(tableName = "upcomingMovies")
data class UpcomingResultList(

    @TypeConverters(UpcomingListConverter::class)
    @ColumnInfo(name = "results") val results: List<UpcomingMovies>
) {
    @PrimaryKey(autoGenerate = true)
    var id = 0
}

data class UpcomingMovies(
    val id: String,
    val title: String,
    val poster_path: String,

    @TypeConverters(ImageConverter::class)
    var bitmap: Bitmap? = null
)
