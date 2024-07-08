package com.example.movieapp.movies.presentation.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.movieapp.R
import com.example.movieapp.movies.domain.MovieDetails
import com.example.movieapp.movies.domain.MovieProductionCompany
import com.example.movieapp.movies.presentation.list.MovieImage

@Composable
fun MovieDetailsScreen(
    state: MovieDetailsScreenState
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {

        val movieDetails = state.movieDetails

        if (movieDetails != null) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                if (movieDetails.posterPath != null) {
                    MovieImage(
                        movieDetails.posterPath,
                        Modifier.padding(top = 32.dp, bottom = 32.dp),
                        stringResource(R.string.movie_poster_size_big),
                    )
                }
                MovieDetails(
                    movieDetails,
                    Modifier.padding(horizontal = 32.dp),
                )
            }
        }
        if (state.isLoading)
            CircularProgressIndicator()
        if (state.error != null)
            Text(state.error)
    }
}

@Composable
fun MovieDetails(
    movieDetails: MovieDetails,
    modifier: Modifier,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start
) {
    Column(modifier = modifier, horizontalAlignment = horizontalAlignment) {
        if (!movieDetails.title.isNullOrBlank()) {
            Text(
                text = movieDetails.title,
                style = MaterialTheme.typography.h5
            )
        }
        CompositionLocalProvider(
            LocalContentAlpha provides ContentAlpha.medium
        ) {
            if (!movieDetails.description.isNullOrBlank()) {
                Text(
                    text = movieDetails.description,
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(top = 16.dp),
                )
            }
            if (!movieDetails.tagline.isNullOrBlank()) {
                Text(
                    text = "Tag line being used is \"${movieDetails.tagline}\"",
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(top = 16.dp),
                )
            }
            if (!movieDetails.releaseDate.isNullOrBlank()) {
                Text(
                    text = "Release date ${movieDetails.releaseDate}",
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(top = 16.dp),
                )
            }
            if (!movieDetails.productionCompanies.isNullOrEmpty()) {
                LazyRow(
                    verticalAlignment = Alignment.Bottom,
                    modifier = Modifier.padding(top = 16.dp),
                ) {
                    items(movieDetails.productionCompanies) { company ->
                        MovieProductionCompany(company)
                    }
                }
            }
        }
    }
}

@Composable
fun MovieProductionCompany(
    company: MovieProductionCompany,
) {
    val baseImageUrl = stringResource(R.string.image_api_base_url)
    val logoSize = stringResource(R.string.company_logo_size)
    val logoUrl = baseImageUrl + logoSize + company.logoPath
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(all = 16.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current).data(logoUrl).build(),
            contentDescription = stringResource(R.string.company_logo),
            contentScale = ContentScale.Fit,
        )
        if (!company.name.isNullOrBlank() && !company.originCountry.isNullOrBlank()) {
            Text(
                text = company.name + ", " + company.originCountry,
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(top = 16.dp),
            )
        }
    }
}
