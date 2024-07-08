package com.example.movieapp.movies.data

import com.example.movieapp.BuildConfig
import com.example.movieapp.movies.data.local.LocalMovie
import com.example.movieapp.movies.data.local.MoviesDao
import com.example.movieapp.movies.data.local.BaseLocalMovie
import com.example.movieapp.movies.data.remote.MovieApiService
import com.example.movieapp.movies.domain.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.net.ConnectException
import java.net.UnknownHostException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
    private val movieApiService: MovieApiService,
    private val moviesDao: MoviesDao,
) {
    suspend fun getMovieList(): List<Movie> {
        return withContext(Dispatchers.IO) {
            return@withContext moviesDao.getAll().map {
                Movie(it.id, it.title, it.description, it.posterPath, it.isFavorite)
            }
        }
    }

    suspend fun loadMovies() {
        return withContext(Dispatchers.IO) {
            try {
                refreshCache()
            } catch (e: Exception) {
                when (e) {
                    is ConnectException,
                    is UnknownHostException,
                    is HttpException -> {
                        if (moviesDao.getAll().isEmpty())
                            throw Exception(
                                "Something went wrong :/" +
                                        "There is no data."
                            )
                    }

                    else -> throw e
                }
            }
        }
    }

    private suspend fun refreshCache() {
        val remoteMovies = movieApiService.getMovieList(
            BuildConfig.API_KEY
        ).results
        val favoriteMovieList = moviesDao.getAllFavoriteMovies()
        moviesDao.addAll(remoteMovies.map {
            LocalMovie(it.id, it.title, it.description, it.posterPath, false)
        })
        moviesDao.updateAll(
            favoriteMovieList.map {
                BaseLocalMovie(id = it.id, isFavorite = true)
            })
    }

    suspend fun toggleFavoriteMovie(
        id: Int,
        value: Boolean
    ) = withContext(Dispatchers.IO) {
        moviesDao.update(
            BaseLocalMovie(id = id, isFavorite = value)
        )
    }
}