package com.example.movieapp.movies.data.remote

import com.google.gson.annotations.SerializedName

data class RemoteMovieDetails(
    @SerializedName("id")
    val id: Int,
    @SerializedName("original_title")
    val title: String?,
    @SerializedName("overview")
    val description: String?,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("production_companies")
    val productionCompanies: List<RemoteMovieProductionCompany>?,
    @SerializedName("release_date")
    val releaseDate: String?,
    @SerializedName("tagline")
    val tagline: String?,
)