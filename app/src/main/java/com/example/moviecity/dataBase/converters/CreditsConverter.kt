package com.example.moviecity.dataBase.converters

import androidx.room.TypeConverter
import com.example.moviecity.models.Cast
import com.example.moviecity.models.CastList

class CreditsConverter {

    @TypeConverter
    fun fromSource(cast: CastList): List<Cast> {
        return cast.cast
    }

    @TypeConverter
    fun toSource(cast: List<Cast>): CastList {
        return CastList(cast)
    }


}