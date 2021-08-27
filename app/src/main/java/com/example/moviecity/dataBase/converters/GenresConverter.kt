package com.example.moviecity.dataBase.converters

import androidx.room.TypeConverter
import com.example.moviecity.models.Genres
import com.google.gson.Gson

class GenresConverter {

    @TypeConverter
    fun listTOJson(value: List<Genres>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonTOList(value: String) = Gson().fromJson(value, Array<Genres>::class.java).toList()

}