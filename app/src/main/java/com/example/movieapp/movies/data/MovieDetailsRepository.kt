package com.example.movieapp.movies.data

import com.example.movieapp.BuildConfig
import com.example.movieapp.movies.data.local.MoviesDao
import com.example.movieapp.movies.data.local.LocalMovieDetails
import com.example.movieapp.movies.data.local.LocalMovieProductionCompany
import com.example.movieapp.movies.data.remote.MovieApiService
import com.example.movieapp.movies.domain.MovieDetails
import com.example.movieapp.movies.domain.MovieProductionCompany
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.net.ConnectException
import java.net.UnknownHostException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieDetailsRepository @Inject constructor(
    private val movieApiService: MovieApiService,
    private val moviesDao: MoviesDao,
) {

    suspend fun getMovieDetails(id: Int): MovieDetails {
        return withContext(Dispatchers.IO) {
            return@withContext moviesDao.getMovieById(id).let {
                MovieDetails(
                    id = it.id,
                    title = it.title,
                    description = it.description,
                    posterPath = it.posterPath,
                    releaseDate = it.releaseDate,
                    tagline = it.tagLine,
                    productionCompanies = it.productionCompanies?.map { remoteProductionCompany ->
                        MovieProductionCompany(
                            logoPath = remoteProductionCompany.logoPath,
                            name = remoteProductionCompany.name,
                            originCountry = remoteProductionCompany.originCountry,
                        )
                    },
                )
            }
        }
    }

    suspend fun loadMovieDetails(id: Int) {
        return withContext(Dispatchers.IO) {
            try {
                refreshDetails(id)
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

    private suspend fun refreshDetails(movieId: Int) {
        val movieDetails = movieApiService.getMovieDetails(
            movieId,
            BuildConfig.API_KEY
        )
        moviesDao.updateWithDetails(
            LocalMovieDetails(
                id = movieId,
                releaseDate = movieDetails.releaseDate,
                tagLine = movieDetails.tagline,
                productionCompanies = movieDetails.productionCompanies?.map {
                    LocalMovieProductionCompany(
                        id = it.id,
                        logoPath = it.logoPath,
                        name = it.name,
                        originCountry = it.originCountry,
                    )
                }
            )
        )
    }
}