package com.example.movieapp.movies.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {

    @GET("3/trending/movie/day")
    suspend fun getMovieList(@Query("api_key") key: String): TrendingMoviesResult

    @GET("3/movie/{id}")
    suspend fun getMovieDetails(
        @Path("id") id: Int, @Query("api_key") key: String
    ): RemoteMovieDetails
}