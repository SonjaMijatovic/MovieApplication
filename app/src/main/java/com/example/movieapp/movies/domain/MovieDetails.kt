package com.example.movieapp.movies.domain

data class MovieDetails(
    val id: Int,
    val title: String?,
    val description: String?,
    val posterPath: String?,
    val releaseDate: String?,
    val tagline: String?,
    val productionCompanies: List<MovieProductionCompany>?,
)

data class MovieProductionCompany(
    val logoPath: String?,
    val name: String?,
    val originCountry: String?,
)