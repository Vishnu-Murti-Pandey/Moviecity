package com.example.moviecity.dataBase.converters

import androidx.room.TypeConverter
import com.example.moviecity.models.UpcomingMovies
import com.google.gson.Gson

class UpcomingListConverter {


    @TypeConverter
    fun listTOJson(value: List<UpcomingMovies>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonTOList(value: String) = Gson().fromJson(value, Array<UpcomingMovies>::class.java).toList()


}