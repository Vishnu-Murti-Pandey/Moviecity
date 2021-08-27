package com.example.moviecity.dataBase.converters

import androidx.room.TypeConverter
import com.example.moviecity.models.PopularMovies
import com.google.gson.Gson

class PopularListConverter {

    @TypeConverter
    fun listTOJson(value: List<PopularMovies>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonTOList(value: String) = Gson().fromJson(value, Array<PopularMovies>::class.java).toList()


}