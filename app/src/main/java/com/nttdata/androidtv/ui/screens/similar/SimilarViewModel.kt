package com.nttdata.androidtv.ui.screens.similar

import com.nttdata.androidtv.data.repository.MoviesRepository
import com.nttdata.androidtv.data.repository.SeriesRepository
import com.nttdata.androidtv.data.response.Movie
import com.nttdata.androidtv.data.response.MovieDetail
import com.nttdata.androidtv.data.response.SeriesDetail
import com.nttdata.androidtv.di.IODispatcher
import com.nttdata.androidtv.ui.navigation.NavigationManager
import com.nttdata.androidtv.ui.navigation.Screens
import com.nttdata.androidtv.ui.screens.detail.MovieDetailState
import com.rocket.android.core.viewmodel.BaseViewModel
import com.rocket.core.domain.error.Failure
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SimilarViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val seriesRepository: SeriesRepository,
    private val navigationManager: NavigationManager,
    @IODispatcher coroutineDispatcher: CoroutineDispatcher
): BaseViewModel(dispatcher = coroutineDispatcher) {

    private val _state = MutableStateFlow(SimilarState())
    val state = _state.asStateFlow()

    private var similarMovies = mutableListOf<Movie>()

    fun navigateToDetail(itemId: String, type: String){
        navigationManager.navigateDetail(Screens.HOME, itemId, type)
    }

    fun getMovies(movieId: Int){
        for (i in 1..100){
            getSimilarMovies(movieId, i)
        }
    }

    private fun getSimilarMovies(movieId: Int, page: Int = 1){
        _state.value = SimilarState(loading = true)
        launch {
            moviesRepository.getSimilarMovies(movieId).fold(
                ::handleSimilarMoviesFailure,
                ::handleSimilarMoviesSuccess
            )
        }
    }

    private fun handleSimilarMoviesFailure(failure: Failure){
        _state.value = SimilarState(errorSimilarMovies = failure.data.toString())
    }

    private fun handleSimilarMoviesSuccess(response: List<Movie>?){
        if (response != null) {
            similarMovies.addAll(response)
        }
        _state.value = SimilarState(similarMovies = similarMovies)
    }

}

data class SimilarState(
    val loading: Boolean = false,
    val similarMovies: List<Movie>? = null,
    val errorSimilarMovies: String? = null

)