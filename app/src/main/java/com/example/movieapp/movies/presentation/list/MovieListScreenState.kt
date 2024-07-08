package com.example.movieapp.movies.presentation.list

import com.example.movieapp.movies.domain.Movie

data class MovieListScreenState(
    val movies: List<Movie>,
    val isLoading: Boolean,
    val error: String? = null,
)