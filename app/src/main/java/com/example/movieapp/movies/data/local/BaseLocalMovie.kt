package com.example.movieapp.movies.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity
class BaseLocalMovie(
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "is_favorite")
    val isFavorite: Boolean
)