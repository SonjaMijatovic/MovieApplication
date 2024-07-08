package com.example.movieapp.movies.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.movieapp.movies.presentation.details.MovieDetailsScreen
import com.example.movieapp.movies.presentation.details.MovieDetailsViewModel
import com.example.movieapp.movies.presentation.list.MovieListScreen
import com.example.movieapp.movies.presentation.list.MovieListViewModel
import com.example.movieapp.ui.theme.MovieAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieAppTheme {
                MovieApp()
            }
        }
    }
}

@Composable
private fun MovieApp() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "movies") {
        composable(
            route = "movies"
        ) {
            val listViewModel: MovieListViewModel = hiltViewModel()
            MovieListScreen(
                state = listViewModel.state.value,
                onItemClick = { id ->
                    navController.navigate("movies/$id")
                },
                onFavoriteClick = { id, oldValue ->
                    listViewModel.toggleFavorite(id, oldValue)
                })
        }
        composable(
            route = "movies/{movie_id}",
            arguments = listOf(navArgument("movie_id") {
                type = NavType.IntType
            }),
        ) {
            val detailsViewModel: MovieDetailsViewModel = hiltViewModel()
            MovieDetailsScreen(
                state = detailsViewModel.state.value
            )
        }
    }
}