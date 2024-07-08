package com.example.movieapp.movies.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "movies")
data class LocalMovie(
    @PrimaryKey()
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "original_title")
    val title: String?,
    @ColumnInfo(name = "overview")
    val description: String?,
    @ColumnInfo(name = "poster_path")
    val posterPath: String?,
    @ColumnInfo(name = "is_favorite")
    val isFavorite: Boolean = false,
    @ColumnInfo(name = "release_date")
    val releaseDate: String? = null,
    @ColumnInfo(name = "tag_line")
    val tagLine: String? = null,
    @TypeConverters(LocalMovieProductionCompanyConverter::class)
    @ColumnInfo(name = "production_companies")
    val productionCompanies: List<LocalMovieProductionCompany>? = null,
)