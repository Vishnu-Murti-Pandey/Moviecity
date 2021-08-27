package com.example.moviecity.models

import android.graphics.Bitmap
import androidx.room.*
import com.example.moviecity.dataBase.converters.CreditsConverter
import com.example.moviecity.dataBase.converters.GenresConverter
import com.example.moviecity.dataBase.converters.ImageConverter


@Entity(tableName = "detailsTable")
data class MovieDetails(

    @ColumnInfo(name = "backdrop") val backdrop_path: String,

    @ColumnInfo(name = "genres")
    @TypeConverters(GenresConverter::class)
    val genres: List<Genres>,


    @ColumnInfo(name = "overview") val overview: String,
    @ColumnInfo(name = "poster") val poster_path: String,
    @ColumnInfo(name = "runtime") val runtime: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "ratings") val vote_average: String,
    @ColumnInfo(name = "releaseDate") val release_date: String,


    @ColumnInfo(name = "credits")
    @TypeConverters(CreditsConverter::class)
    val credits: CastList,


    @TypeConverters(ImageConverter::class)
    var bitmapPoster: Bitmap? = null,

    @TypeConverters(ImageConverter::class)
    var bitmapBackDrop: Bitmap? = null,


    ) {
    @PrimaryKey(autoGenerate = true)
    var id = 0
}


