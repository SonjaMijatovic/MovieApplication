package com.example.movieapp.movies.data.local

import androidx.room.ColumnInfo
import androidx.room.TypeConverters

class LocalMovieDetails(
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "release_date")
    val releaseDate: String?,
    @ColumnInfo(name = "tag_line")
    val tagLine: String?,
    @TypeConverters(LocalMovieProductionCompanyConverter::class)
    @ColumnInfo(name = "production_companies")
    val productionCompanies: List<LocalMovieProductionCompany>?,
)