package com.example.movieapp.movies.domain.list

import com.example.movieapp.movies.data.MovieRepository
import com.example.movieapp.movies.domain.Movie
import javax.inject.Inject

class GetInitialMovieListUseCase @Inject constructor(
    private val repository: MovieRepository,
    private val getSortedMovieListUseCase: GetSortedMovieListUseCase,
) {
    suspend operator fun invoke(): List<Movie> {
        repository.loadMovies()
        return getSortedMovieListUseCase()
    }
}