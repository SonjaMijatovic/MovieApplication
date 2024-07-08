package com.example.movieapp.movies.domain

import com.example.movieapp.movies.data.MovieRepository
import com.example.movieapp.movies.domain.list.GetSortedMovieListUseCase
import javax.inject.Inject

class ToggleMovieFavoriteValueUseCase @Inject constructor(
    private val repository: MovieRepository,
    private val getSortedMovieListUseCase: GetSortedMovieListUseCase,
) {

    suspend operator fun invoke(id: Int, oldValue: Boolean): List<Movie> {
        val newValue = oldValue.not()
        repository.toggleFavoriteMovie(id, newValue)
        return getSortedMovieListUseCase()
    }
}