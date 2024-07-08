package com.example.movieapp.movies.data.remote

import com.google.gson.annotations.SerializedName

data class RemoteMovieProductionCompany(
    @SerializedName("id")
    val id: Int,
    @SerializedName("logo_path")
    val logoPath: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("origin_country")
    val originCountry: String?,
)
