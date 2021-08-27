package com.example.moviecity.dataBase.converters

import androidx.room.TypeConverter
import com.example.moviecity.models.Movies
import com.google.gson.Gson

class ViewAllListConverter {

    @TypeConverter
    fun listTOJson(value: List<Movies>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonTOList(value: String) = Gson().fromJson(value, Array<Movies>::class.java).toList()

}