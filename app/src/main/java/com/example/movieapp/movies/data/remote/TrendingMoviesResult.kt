package com.example.movieapp.movies.data.remote

import com.google.gson.annotations.SerializedName

data class TrendingMoviesResult(
    @SerializedName("results")
    val results: List<RemoteMovie>,
)