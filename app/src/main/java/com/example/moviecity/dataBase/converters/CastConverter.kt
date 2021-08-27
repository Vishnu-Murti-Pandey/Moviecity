package com.example.moviecity.dataBase.converters

import androidx.room.TypeConverter
import com.example.moviecity.models.Cast
import com.google.gson.Gson

class CastConverter {


    @TypeConverter
    fun listTOJson(value: List<Cast>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonTOList(value: String) = Gson().fromJson(value, Array<Cast>::class.java).toList()


}