package com.example.moviecity.models

import android.graphics.Bitmap
import androidx.room.TypeConverters
import com.example.moviecity.dataBase.converters.CastConverter
import com.example.moviecity.dataBase.converters.ImageConverter

@TypeConverters(CastConverter::class)
data class CastList(val cast: List<Cast>)

data class Cast(
    val name: String,
    val profile_path: String,

    @TypeConverters(ImageConverter::class)
    var bitmap: Bitmap? = null

)

