package com.nttdata.androidtv.ui.screens.home

import com.nttdata.androidtv.data.repository.MoviesRepository
import com.nttdata.androidtv.data.repository.SeriesRepository
import com.nttdata.androidtv.data.response.Movie
import com.nttdata.androidtv.data.response.Series
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
class HomeViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val seriesRepository: SeriesRepository,
    private val navigationManager: NavigationManager,
    @IODispatcher coroutineDispatcher: CoroutineDispatcher
): BaseViewModel(dispatcher = coroutineDispatcher) {

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    private var nowPlaying = emptyList<Movie>()
    private var movies = emptyList<Movie>()
    private var series = emptyList<Series>()

    init {
        getNowPlaying()
        getMovies()
        getSeries()
    }

    fun navigateToDetail(itemId: String, type: String){
        navigationManager.navigateDetail(Screens.HOME, itemId, type)
    }

    fun navigateTo(){
        navigationManager.navigate(Screens.SIMILAR)
    }

    private fun load(){
        nowPlaying = emptyList()
        movies = emptyList()
        series = emptyList()
        _state.value = HomeState(loading = true)
    }

    fun getNowPlaying(){
        launch {
            load()
            moviesRepository.getNowPlaying().fold(
                ::handleNowPlayingFailure,
                ::handleNowPlayingSuccess
            )
        }
    }

    private fun handleNowPlayingFailure(failure: Failure){
        _state.value = HomeState(errorNowPlaying = failure.data.toString(), movies = movies, series = series)
    }

    private fun handleNowPlayingSuccess(response: List<Movie>?){
        nowPlaying = response ?: emptyList()
        _state.value = HomeState(nowPlaying = nowPlaying, movies = movies, series = series)
    }

    private fun getMovies(){
        launch {
            moviesRepository.getMovies().fold(
                ::handleMoviesFailure,
                ::handleMoviesSuccess
            )
        }
    }

    private fun handleMoviesFailure(failure: Failure){
        _state.value = HomeState(errorMovies = failure.data.toString(), nowPlaying = nowPlaying, series = series)
    }

    private fun handleMoviesSuccess(response: List<Movie>?){
        movies = response ?: emptyList()
        _state.value = HomeState(nowPlaying = nowPlaying, movies = movies, series = series)
    }

    private fun getSeries(){
        launch {
            seriesRepository.getSeries().fold(
                ::handleSeriesFailure,
                ::handleSeriesSuccess
            )
        }
    }

    private fun handleSeriesFailure(failure: Failure){
        _state.value = HomeState(errorSeries = failure.data.toString(), nowPlaying = nowPlaying, movies = movies)
    }

    private fun handleSeriesSuccess(response: List<Series>?){
        _state.value = HomeState(series = response ?: emptyList(), nowPlaying = nowPlaying, movies = movies)
    }
}

data class HomeState(
    val loading: Boolean = false,
    val nowPlaying: List<Movie> = emptyList(),
    val movies: List<Movie> = emptyList(),
    val series: List<Series> = emptyList(),
    val errorNowPlaying: String? = null,
    val errorMovies: String? = null,
    val errorSeries: String? = null

)