package com.example.movieapp.movies.presentation.list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.movies.domain.list.GetInitialMovieListUseCase
import com.example.movieapp.movies.domain.ToggleMovieFavoriteValueUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val getInitialMovieListUseCase: GetInitialMovieListUseCase,
    private val toggleMovieFavoriteValueUseCase: ToggleMovieFavoriteValueUseCase,
): ViewModel() {
    private val _state = mutableStateOf(
        MovieListScreenState(
            movies = listOf(),
            isLoading = true)
    )
    val state: State<MovieListScreenState>
        get() = _state

    private val errorHandler = CoroutineExceptionHandler { _, exception ->
        exception.printStackTrace()
        _state.value = _state.value.copy(error = exception.message, isLoading = false)
    }

    private fun getMovies() {
        viewModelScope.launch(errorHandler) {
            val movies = getInitialMovieListUseCase()
            _state.value = _state.value.copy(
                movies = movies,
                isLoading = false)
        }
    }

    init {
        getMovies()
    }

    fun toggleFavorite(id: Int, oldValue: Boolean) {
        viewModelScope.launch(errorHandler) {
            val updatedMovies =
                toggleMovieFavoriteValueUseCase(id, oldValue)
            _state.value = _state.value.copy(movies = updatedMovies)
        }
    }
}