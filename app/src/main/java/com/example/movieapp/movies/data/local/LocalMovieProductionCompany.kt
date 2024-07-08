package com.example.movieapp.movies.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.example.movieapp.movies.domain.MovieProductionCompany

class LocalMovieProductionCompany(
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "logoPath")
    val logoPath: String?,
    @ColumnInfo(name = "name")
    val name: String?,
    @ColumnInfo(name = "originCountry")
    val originCountry: String?,
)