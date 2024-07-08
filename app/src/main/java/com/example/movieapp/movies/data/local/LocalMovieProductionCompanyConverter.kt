package com.example.movieapp.movies.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class LocalMovieProductionCompanyConverter {
    @TypeConverter
    fun fromList(value: List<LocalMovieProductionCompany>?): String? {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toList(value: String?): List<LocalMovieProductionCompany>? {
        val listType = object : TypeToken<List<LocalMovieProductionCompany>>() {}.type
        return Gson().fromJson(value, listType)
    }
}