package com.example.movieapp.movies.domain.details

import com.example.movieapp.movies.data.MovieDetailsRepository
import com.example.movieapp.movies.domain.MovieDetails
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(
    private val repository: MovieDetailsRepository,
) {
    suspend operator fun invoke(id: Int): MovieDetails {
        repository.loadMovieDetails(id)
        return repository.getMovieDetails(id)
    }
}
