package com.example.movieapp.movies.domain.list

import com.example.movieapp.movies.data.MovieRepository
import com.example.movieapp.movies.domain.Movie
import javax.inject.Inject

class GetSortedMovieListUseCase @Inject constructor(
    private val repository: MovieRepository,
) {
    suspend operator fun invoke(): List<Movie> {
        return repository.getMovieList().sortedBy { it.title }
    }
}