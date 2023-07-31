package com.nttdata.androidtv.ui.screens.detail

import com.nttdata.androidtv.data.repository.MoviesRepository
import com.nttdata.androidtv.data.response.Movie
import com.nttdata.androidtv.data.response.MovieDetail
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
class MovieDetailViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val navigationManager: NavigationManager,
    @IODispatcher coroutineDispatcher: CoroutineDispatcher
): BaseViewModel(dispatcher = coroutineDispatcher) {

    private val _state = MutableStateFlow(MovieDetailState())
    val state = _state.asStateFlow()

    private var detail: MovieDetail? = null
    private var similarMovies: List<Movie>? = null

    fun navigateToSimilar(itemId: String, type: String){
        navigationManager.navigateSimilar(Screens.SIMILAR, itemId, type)
    }

    fun navigateToDetail(itemId: String, type: String){
        navigationManager.navigateDetail(Screens.SIMILAR, itemId, type)
    }

    private fun load(){
        _state.value = MovieDetailState(loading = true)
    }

    fun getDetail(itemId: Int){
        getMovieDetail(itemId)
        getSimilarMovies(itemId)
    }

    private fun getMovieDetail(itemId: Int){
        launch {
            load()
            moviesRepository.getMovieDetail(itemId).fold(
                ::handleMovieDetailFailure,
                ::handleMovieDetailSuccess
            )
        }
    }

    private fun handleMovieDetailFailure(failure: Failure){
        detail = null
        _state.value = MovieDetailState(errorMovieDetail = failure.data.toString(), movieDetail = detail)
    }

    private fun handleMovieDetailSuccess(response: MovieDetail?){
        detail = response
        _state.value = MovieDetailState(movieDetail = detail)
    }



    private fun getSimilarMovies(movieId: Int){
        launch {
            moviesRepository.getSimilarMovies(movieId).fold(
                ::handleSimilarMoviesFailure,
                ::handleSimilarMoviesSuccess
            )
        }
    }

    private fun handleSimilarMoviesFailure(failure: Failure){
        similarMovies = null
        _state.value = MovieDetailState(errorSimilarMovies = failure.data.toString(), movieDetail = detail, similarMovies = similarMovies)
    }

    private fun handleSimilarMoviesSuccess(response: List<Movie>?){
        similarMovies = response
        _state.value = MovieDetailState(similarMovies = similarMovies, movieDetail = detail)
    }
}

data class MovieDetailState(
    val loading: Boolean = false,
    val errorMovieDetail: String? = null,
    val movieDetail: MovieDetail? = null,
    val errorSeriesDetail: String? = null,
    val seriesDetail: SeriesDetail? = null,
    val errorSimilarMovies: String? = null,
    val similarMovies: List<Movie>? = null

)