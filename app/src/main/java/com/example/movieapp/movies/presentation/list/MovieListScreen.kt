package com.example.movieapp.movies.presentation.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.movieapp.R
import com.example.movieapp.movies.domain.Movie


@Composable
fun MovieListScreen(
    state: MovieListScreenState,
    onItemClick: (id: Int) -> Unit,
    onFavoriteClick: (id: Int, oldValue: Boolean) -> Unit
) {
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Trending Movies",
            style = MaterialTheme.typography.h4,
        )
        Image(
            painter = painterResource(id = R.drawable.logo_tmdb),
            contentDescription = "The movie database logo"
        )
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            LazyColumn(
                contentPadding = PaddingValues(
                    vertical = 8.dp,
                    horizontal = 8.dp
                )
            ) {
                items(state.movies) { movie ->
                    MovieItem(movie,
                        onFavoriteClick = { id, oldValue ->
                            onFavoriteClick(id, oldValue)
                        },
                        onItemClick = { id -> onItemClick(id) })
                }
            }
            if (state.isLoading)
                CircularProgressIndicator()
            if (state.error != null)
                Text(state.error)
        }
    }
}

@Composable
fun MovieImage(
    posterPath: String,
    modifier: Modifier = Modifier,
    posterSize: String? = stringResource(R.string.movie_poster_size_small),
    onClick: () -> Unit = { },
) {
    val baseImageUrl = stringResource(R.string.image_api_base_url)
    val posterUrl = baseImageUrl + posterSize + posterPath

    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current).data(posterUrl).build(),
        contentDescription = stringResource(R.string.movie_poster),
        contentScale = ContentScale.Fit,
        modifier = modifier.clickable { onClick() },
    )
}

@Composable
fun MovieIcon(icon: ImageVector, modifier: Modifier, onClick: () -> Unit = { }) {
    Image(
        imageVector = icon,
        contentDescription = stringResource(R.string.favorite_icon),
        modifier = modifier
            .padding(8.dp)
            .clickable { onClick() })
}

@Composable
fun MovieInfo(
    title: String,
    description: String,
    modifier: Modifier,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start
) {
    Column(modifier = modifier, horizontalAlignment = horizontalAlignment) {
        Text(
            text = title,
            style = MaterialTheme.typography.h6
        )
        CompositionLocalProvider(
            LocalContentAlpha provides ContentAlpha.medium
        ) {
            Text(
                text = description,
                style = MaterialTheme.typography.body2
            )
        }
    }
}

@Composable
fun MovieItem(
    item: Movie,
    onFavoriteClick: (id: Int, oldValue: Boolean) -> Unit,
    onItemClick: (id: Int) -> Unit
) {
    val icon = if (item.isFavorite)
        Icons.Filled.Favorite
    else
        Icons.Filled.FavoriteBorder
    Card(
        elevation = 4.dp,
        modifier = Modifier
            .padding(8.dp)
            .clickable { onItemClick(item.id) }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            if (!item.posterPath.isNullOrBlank()) {
                MovieImage(item.posterPath)
            }
            if (!item.title.isNullOrBlank() && !item.description.isNullOrBlank()) {
                MovieInfo(
                    item.title,
                    item.description,
                    Modifier
                        .weight(0.7f)
                        .padding(start = 16.dp)
                )
            }
            MovieIcon(icon, Modifier.weight(0.15f)) {
                onFavoriteClick(item.id, item.isFavorite)
            }
        }
    }
}