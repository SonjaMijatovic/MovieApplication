package com.example.movieapp.movies.presentation.details

import com.example.movieapp.movies.domain.MovieDetails

data class MovieDetailsScreenState(
    val movieDetails: MovieDetails? = null,
    val isLoading: Boolean,
    val error: String? = null,
)