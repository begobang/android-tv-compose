package com.nttdata.androidtv.ui.screens.detail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.tv.foundation.lazy.list.TvLazyColumn
import androidx.tv.foundation.lazy.list.TvLazyRow
import androidx.tv.material3.Border
import androidx.tv.material3.Button
import androidx.tv.material3.CardDefaults
import androidx.tv.material3.ClassicCard
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import com.nttdata.androidtv.R
import com.nttdata.androidtv.data.response.Movie
import com.nttdata.androidtv.data.response.MovieDetail
import com.nttdata.androidtv.data.response.ProductionCompanies
import com.nttdata.androidtv.ui.composables.CatalogMovies
import com.nttdata.androidtv.ui.composables.HomeLabel
import com.nttdata.androidtv.ui.composables.Image
import com.nttdata.androidtv.ui.composables.Progress

@Composable
fun MovieDetail(itemId: String, viewModel: MovieDetailViewModel = hiltViewModel()) {

    val state by viewModel.state.collectAsState()

    viewModel.getDetail(itemId.toInt())

    MovieDetailScreen(
        loading = state.loading,
        movie = state.movieDetail,
        similarMovies = state.similarMovies,
        onClickDetail = {
            viewModel.navigateToDetail("${it.id}", "movie")
        }
    ) {
        viewModel.navigateToSimilar(itemId, "movie")
    }

}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun MovieDetailScreen(
    loading: Boolean,
    movie: MovieDetail?,
    similarMovies: List<Movie>?,
    onClickDetail: (Movie) -> Unit,
    onClickSimilar: () -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()) {
        Progress(loading = loading)

        if (movie != null) {
            TvLazyColumn {
                item {
                    MovieWallpaper(movie = movie)
                }

                item {
                    Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                        HomeLabel(text = stringResource(id = R.string.companies), topDp = 20.dp)
                        TvLazyRow(
                            contentPadding = PaddingValues(
                                start = 50.dp,
                                end = 50.dp,
                                bottom = 20.dp
                            )
                        ) {
                            items(movie.production_companies.size) {
                                CompaniesItem(company = movie.production_companies[it])
                            }
                        }


                    }
                }

                similarMovies?.let { similar ->
                    if(similar.isNotEmpty()){
                        item {
                            Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                                HomeLabel(text = stringResource(id = R.string.similar_content, movie.original_title))
                                CatalogMovies(movies = similar, onClickMovie = onClickDetail)
                            }
                        }

                        item {
                            Button(onClick = { onClickSimilar() }) {
                                Text(text = "View More")
                            }
                        }
                    }
                }

            }
        }


    }


}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun CompaniesItem(company: ProductionCompanies) {
    var focus by rememberSaveable { mutableStateOf(false) }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row {
            ClassicCard(
                onClick = { },
                image = {
                    Image(
                        path = company.logo_path ?: "",
                        contentDescription = company.name,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.size(100.dp)
                    )
                },
                title = {
                },
                shape = CardDefaults.shape(
                    shape = MaterialTheme.shapes.small,
                    pressedShape = MaterialTheme.shapes.small
                ),
                border = CardDefaults.border(
                    focusedBorder = Border(
                        border = BorderStroke(
                            2.dp,
                            MaterialTheme.colorScheme.primary
                        ),
                        shape = MaterialTheme.shapes.small
                    )
                ),
                modifier = Modifier.onFocusChanged {
                    focus = it.isFocused
                }
            )

            Spacer(modifier = Modifier.width(30.dp))


        }

        Spacer(modifier = Modifier.height(10.dp))

        if (focus) {
            Row {
                Text(text = company.name, style = MaterialTheme.typography.bodyMedium, textAlign = TextAlign.Center)
                Spacer(modifier = Modifier.width(30.dp))
            }
        }
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun MovieWallpaper(movie: MovieDetail) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(500.dp)
    ) {
        Box {
            Image(
                path = movie.poster_path,
                contentDescription = movie.title,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(shape = MaterialTheme.shapes.extraLarge),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.8f)
                            )
                        )
                    )
                    .padding(horizontal = 100.dp),
                verticalArrangement = Arrangement.Bottom,

                ) {
                Spacer(modifier = Modifier.fillMaxHeight(0.2f))
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.displayLarge,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(20.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "${movie.vote_average}",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Green
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "vote_average",
                        tint = Color.Green,
                        modifier = Modifier
                            .size(12.dp)
                            .align(Alignment.CenterVertically)
                    )

                    Spacer(modifier = Modifier.width(20.dp))

                    Text(
                        text = movie.release_date.substringBefore('-'),
                        style = MaterialTheme.typography.labelSmall
                    )

                    Spacer(modifier = Modifier.width(20.dp))

                    Icon(
                        imageVector = Icons.Default.Language,
                        contentDescription = "language",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .size(12.dp)
                            .align(Alignment.CenterVertically)
                    )

                    Text(
                        text = movie.original_language.uppercase(),
                        style = MaterialTheme.typography.labelSmall
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    movie.genres.forEach {
                        Button(onClick = { }) {
                            Text(text = it.name, style = MaterialTheme.typography.labelSmall)
                        }

                        Spacer(modifier = Modifier.width(10.dp))
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = movie.overview,
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.fillMaxWidth(0.7f)
                )

                Spacer(modifier = Modifier.height(50.dp))
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.8f)
                            )
                        )
                    )
            )
        }
    }
}