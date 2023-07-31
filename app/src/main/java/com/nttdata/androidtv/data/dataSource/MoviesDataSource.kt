package com.nttdata.androidtv.data.dataSource

import com.nttdata.androidtv.data.response.Movie
import com.nttdata.androidtv.data.response.MovieDetail
import com.nttdata.androidtv.data.services.MoviesService
import com.rocket.android.core.data.network.datasource.BaseNetworkDatasource
import com.rocket.core.crashreporting.logger.CrashLogger
import com.rocket.core.domain.error.Failure
import com.rocket.core.domain.functional.Either
import javax.inject.Inject

class MoviesDataSource @Inject constructor(
    private val service: MoviesService,
    crashLogger: CrashLogger
): BaseNetworkDatasource(crashLogger) {

    fun getNowPlaying(): Either<Failure, List<Movie>?> {
        return requestGenericApi(
            call = { service.getNowPlaying() },
            parserSuccess = { it?.results }
        )
    }

    fun getMovies(): Either<Failure, List<Movie>?> {
        return requestGenericApi(
            call = { service.getMovies() },
            parserSuccess = { it?.results }
        )
    }

    fun getSimilarMovies(movieId: Int, page: Int = 1): Either<Failure, List<Movie>?> {
        return requestGenericApi(
            call = { service.getSimilarMovies(movieId, page) },
            parserSuccess = { it?.results }
        )
    }

    fun getMovieDetail(movieId: Int): Either<Failure, MovieDetail?> {
        return requestGenericApi(
            call = { service.getDetailMovie(movieId) },
            parserSuccess = { it }
        )
    }
}