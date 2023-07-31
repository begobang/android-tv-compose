package com.nttdata.androidtv.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.tv.foundation.lazy.list.TvLazyColumn
import androidx.tv.material3.CarouselState
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import com.nttdata.androidtv.R
import com.nttdata.androidtv.data.response.*
import com.nttdata.androidtv.ui.composables.*

@Composable
fun Home(viewModel: HomeViewModel = hiltViewModel()) {

    val state by viewModel.state.collectAsState()

    HomeScreen(
        loading = state.loading,
        nowPlaying = state.nowPlaying,
        movies = state.movies,
        series = state.series,
        onClickSeries = {
            viewModel.navigateToDetail("${it.id}", "tv")
        },
        onClickMovie = { movie ->
            viewModel.navigateToDetail("${movie.id}", "movie")
        })

}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun HomeScreen(
    loading: Boolean,
    nowPlaying: List<Movie>,
    movies: List<Movie>,
    series: List<Series>,
    onClickMovie: (Movie) -> Unit,
    onClickSeries: (Series) -> Unit,
) {

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()) {
        Progress(loading = loading && nowPlaying.isEmpty())

        if (movies.isNotEmpty() || series.isNotEmpty()) {
            Catalog(
                nowPlaying = nowPlaying,
                movies = movies,
                onClick = onClickMovie,
                series = series,
                onClickSeries = onClickSeries
            )
        }
    }

}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun Catalog(
    nowPlaying: List<Movie>,
    movies: List<Movie>,
    onClick: (Movie) -> Unit,
    series: List<Series>,
    onClickSeries: (Series) -> Unit,
) {

    val carouselState = remember { CarouselState() }

    var focus by rememberSaveable { mutableStateOf(false) }


    Column(modifier = Modifier
        .padding(horizontal = 10.dp, vertical = 30.dp)
        .fillMaxSize()) {

        TvLazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            if (nowPlaying.isNotEmpty()) {
                item {
                    MovieCarousel(itemCount = nowPlaying.size, carouselState = carouselState) {
                        nowPlaying[it].also { movie ->
                            CarouselNowPlaying(movie = movie, onClick = onClick)
                        }
                    }
                }
            }

            if (series.isNotEmpty()) {

                item {
                    HomeLabel(text = stringResource(id = R.string.series))
                }

                item {
                    CatalogSeries(series = series, onClickSeries = onClickSeries)
                }


            }


            if (movies.isNotEmpty()) {
                item {
                    HomeLabel(text = stringResource(id = R.string.movies))
                }
                item {
                    CatalogMovies(movies = movies, onClickMovie = onClick)
                }
            }
        }
    }

}

