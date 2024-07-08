package com.example.movieapp.movies.presentation.details

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.movies.domain.details.GetMovieDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    stateHandler: SavedStateHandle,
) : ViewModel() {

    private val _state = mutableStateOf(
        MovieDetailsScreenState(
            movieDetails = null,
            isLoading = true
        )
    )
    val state: State<MovieDetailsScreenState>
        get() = _state

    private val errorHandler = CoroutineExceptionHandler { _, exception ->
        exception.printStackTrace()
        _state.value = _state.value.copy(error = exception.message, isLoading = false)
    }

    init {
        val id = stateHandler.get<Int>("movie_id") ?: 0
        viewModelScope.launch(errorHandler) {
            val movieDetails = getMovieDetailsUseCase(id)
            _state.value = _state.value.copy(
                movieDetails = movieDetails,
                isLoading = false
            )
        }
    }

}