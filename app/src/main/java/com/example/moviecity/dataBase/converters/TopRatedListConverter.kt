package com.example.moviecity.dataBase.converters

import androidx.room.TypeConverter
import com.example.moviecity.models.TopRatedMovies
import com.google.gson.Gson

class TopRatedListConverter {

    @TypeConverter
    fun listTOJson(value: List<TopRatedMovies>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonTOList(value: String) = Gson().fromJson(value, Array<TopRatedMovies>::class.java).toList()


}