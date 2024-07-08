package com.example.movieapp.movies.domain

data class Movie(
    val id: Int,
    val title: String?,
    val description: String?,
    val posterPath: String?,
    val isFavorite: Boolean = false,
)