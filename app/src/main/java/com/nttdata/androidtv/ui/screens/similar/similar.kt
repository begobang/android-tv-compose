package com.nttdata.androidtv.ui.screens.similar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.tv.foundation.lazy.grid.TvGridCells
import androidx.tv.foundation.lazy.grid.TvLazyVerticalGrid
import com.nttdata.androidtv.R
import com.nttdata.androidtv.data.response.Movie
import com.nttdata.androidtv.ui.composables.CatalogCard
import com.nttdata.androidtv.ui.composables.HomeLabel
import com.nttdata.androidtv.ui.composables.Progress

@Composable
fun Similar(itemId: Int, viewModel: SimilarViewModel = hiltViewModel()) {

    val state by viewModel.state.collectAsState()

    viewModel.getMovies(itemId)
    
    SimilarScreen(loading = state.loading, movies = state.similarMovies) {
        viewModel.navigateToDetail("${it.id}", "movie")
    }

}

@Composable
fun SimilarScreen(loading: Boolean, movies: List<Movie>?, onClickMovie: (Movie) -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()) {
        Progress(loading = loading)

        if(!movies.isNullOrEmpty()){
            HomeLabel(text = stringResource(id = R.string.similar))
            TvLazyVerticalGrid(columns = TvGridCells.Adaptive(150.dp)) {
                items(movies.size) {
                    CatalogCard(title = movies[it].original_title, path = movies[it].poster_path) {
                        onClickMovie(movies[it])
                    }
                }
            }
        }


    }
    
}