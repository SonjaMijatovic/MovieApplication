package com.example.movieapp.movies.data.remote

import com.google.gson.annotations.SerializedName

data class RemoteMovie(
    @SerializedName("id")
    val id: Int,
    @SerializedName("original_title")
    val title: String?,
    @SerializedName("overview")
    val description: String?,
    @SerializedName("poster_path")
    val posterPath: String?,
)