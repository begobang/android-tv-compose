package com.nttdata.androidtv.ui.composables

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.tv.material3.Carousel
import androidx.tv.material3.CarouselDefaults
import androidx.tv.material3.CarouselState
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import com.nttdata.androidtv.data.response.Movie
import java.util.Locale

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun MovieCarousel(
    itemCount: Int,
    carouselState: CarouselState,
    content: @Composable AnimatedContentScope.(index: Int) -> Unit,
) {
    Carousel(
        itemCount = itemCount,
        modifier = Modifier
            .fillMaxWidth(),
        carouselState = carouselState,
        carouselIndicator = {
            MovieCarouselIndicatorRow(
                itemCount = itemCount,
                carouselState = carouselState,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(20.dp)
            )
        }
    ) {
        content(this, it)
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun MovieCarouselIndicatorRow(itemCount: Int, carouselState: CarouselState, modifier: Modifier) {
    CarouselDefaults.IndicatorRow(
        itemCount = itemCount,
        activeItemIndex = carouselState.activeItemIndex,
        modifier = modifier,
        indicator = { isActive ->
            MovieCarouselIndicator(isActive)
        }
    )
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun MovieCarouselIndicator(isActive: Boolean) {
    Box(
        modifier = Modifier
            .size(4.dp)
            .background(
                color = if (isActive) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSecondary,
                shape = CircleShape,
            ),
    )
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun CarouselNowPlaying(movie: Movie, onClick: (Movie) -> Unit) {

    Box(modifier = Modifier
        .fillMaxWidth()
        .height(350.dp)
        .clickable { onClick(movie) }) {
        Box {
            Image(
                path = movie.backdrop_path,
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
                    style = MaterialTheme.typography.headlineLarge,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(20.dp))

                Row {
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

                }

                Text(
                    text = movie.overview,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth(0.4f)
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