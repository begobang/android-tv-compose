package com.nttdata.androidtv.ui.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.tv.foundation.lazy.list.TvLazyRow
import androidx.tv.material3.Border
import androidx.tv.material3.Card
import androidx.tv.material3.CardDefaults
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.MaterialTheme
import com.nttdata.androidtv.data.response.Movie
import com.nttdata.androidtv.data.response.Series

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun CatalogCard(title: String, path: String?, onClickMovie: () -> Unit) {
    Card(modifier = Modifier
        .padding(start = 5.dp, end = 5.dp, bottom = 10.dp)
        .width(120.dp)
        .height(180.dp),
        border = CardDefaults.border(focusedBorder = Border(border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary))),
        onClick = onClickMovie
    ) {
        Image(
            path = path,
            contentDescription = title,
            contentScale = ContentScale.FillHeight
        )
    }
}

@Composable
fun CatalogMovies(movies: List<Movie>, onClickMovie: (Movie) -> Unit){
    Column(Modifier.padding(horizontal = 10.dp)) {
        TvLazyRow(modifier = Modifier.padding(horizontal = 20.dp)) {
            items(movies.size) {
                CatalogCard(title = movies[it].title, path = movies[it].poster_path) {
                    onClickMovie(movies[it])
                }
            }
        }
    }
}

@Composable
fun CatalogSeries(series: List<Series>, onClickSeries: (Series) -> Unit){
    Column(Modifier.padding(20.dp)) {
        TvLazyRow(modifier = Modifier.padding(horizontal = 20.dp)) {
            items(series.size) {
                CatalogCard(title = series[it].name, path = series[it].poster_path) {
                    onClickSeries(series[it])
                }
            }
        }
    }
}