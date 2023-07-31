package com.nttdata.androidtv.ui.screens.detail

import com.nttdata.androidtv.data.repository.SeriesRepository
import com.nttdata.androidtv.data.response.Movie
import com.nttdata.androidtv.data.response.MovieDetail
import com.nttdata.androidtv.data.response.Series
import com.nttdata.androidtv.data.response.SeriesDetail
import com.nttdata.androidtv.di.IODispatcher
import com.nttdata.androidtv.ui.navigation.NavigationManager
import com.nttdata.androidtv.ui.navigation.Screens
import com.rocket.android.core.viewmodel.BaseViewModel
import com.rocket.core.domain.error.Failure
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SeriesDetailViewModel @Inject constructor(
    private val seriesRepository: SeriesRepository,
    private val navigationManager: NavigationManager,
    @IODispatcher coroutineDispatcher: CoroutineDispatcher,
) : BaseViewModel(dispatcher = coroutineDispatcher) {

    private val _state = MutableStateFlow(SeriesDetailState())
    val state = _state.asStateFlow()

    private var series: SeriesDetail? = null
    private var similarSeries: List<Series>? = null

    fun navigateToSimilar(itemId: String, type: String){
        navigationManager.navigate(Screens.SIMILAR)
    }

    fun getDetail(itemId: Int){
        getSeriesDetail(itemId)
        getSimilarSeries(itemId)
    }

    private fun load(){
        _state.value = SeriesDetailState(loading = true)
    }


    private fun getSeriesDetail(itemId: Int) {
        launch {
            load()
            seriesRepository.getSeriesDetail(itemId).fold(
                ::handleSeriesDetailFailure,
                ::handleSeriesDetailSuccess
            )
        }
    }

    private fun handleSeriesDetailFailure(failure: Failure){
        series = null
        _state.value = SeriesDetailState(errorSeriesDetail = failure.data.toString(), seriesDetail = series)
    }

    private fun handleSeriesDetailSuccess(response: SeriesDetail?){
        series = response
        _state.value = SeriesDetailState(seriesDetail = series, similarSeries = similarSeries)
    }

    private fun getSimilarSeries(tvId: Int){
        launch {
            seriesRepository.getSimilarMovies(tvId).fold(
                ::handleSimilarMoviesFailure,
                ::handleSimilarMoviesSuccess
            )
        }
    }

    private fun handleSimilarMoviesFailure(failure: Failure){
        similarSeries = null
        _state.value = SeriesDetailState(errorSimilarSeries = failure.data.toString(), seriesDetail = series, similarSeries = similarSeries)
    }

    private fun handleSimilarMoviesSuccess(response: List<Series>?){
        similarSeries = response
        _state.value = SeriesDetailState(similarSeries = similarSeries, seriesDetail = series)
    }



}


data class SeriesDetailState(
    val loading: Boolean = false,
    val errorSeriesDetail: String? = null,
    val seriesDetail: SeriesDetail? = null,
    val errorSimilarSeries: String? = null,
    val similarSeries: List<Series>? = null
)