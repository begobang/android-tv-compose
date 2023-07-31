package com.nttdata.androidtv.ui.screens.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.nttdata.androidtv.data.response.Series
import com.nttdata.androidtv.data.response.SeriesDetail
import com.nttdata.androidtv.ui.composables.Progress

@Composable
fun SeriesDetail(itemId: String, viewModel: SeriesDetailViewModel = hiltViewModel()) {

    val state by viewModel.state.collectAsState()

    viewModel.getDetail(itemId.toInt())

    SeriesDetailScreen(loading = state.loading, series = state.seriesDetail, similarSeries = state.similarSeries) {
        viewModel.navigateToSimilar("${it.id}", "tv")
    }

}

@Composable
fun SeriesDetailScreen(
    loading: Boolean,
    series: SeriesDetail?,
    similarSeries: List<Series>?,
    onClickSimilar: (Series) -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()) {
        Progress(loading = loading)
    }
}