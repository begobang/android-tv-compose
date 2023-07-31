package com.nttdata.androidtv.data.repository

import com.nttdata.androidtv.data.dataSource.MoviesDataSource
import com.nttdata.androidtv.data.response.ApiResponse
import com.nttdata.androidtv.data.response.Movie
import com.nttdata.androidtv.data.response.MovieDetail
import com.nttdata.androidtv.data.services.MoviesService
import com.rocket.core.domain.error.Failure
import com.rocket.core.domain.functional.Either
import retrofit2.await
import javax.inject.Inject

class MoviesRepository @Inject constructor(private val remoteDataSource: MoviesDataSource) {

    fun getNowPlaying(): Either<Failure, List<Movie>?> {
        return remoteDataSource.getNowPlaying()
    }

    fun getMovies(): Either<Failure, List<Movie>?> {
        return remoteDataSource.getMovies()
    }

    fun getSimilarMovies(movieId: Int, page: Int = 1): Either<Failure, List<Movie>?> {
        return remoteDataSource.getSimilarMovies(movieId, page)
    }

    fun getMovieDetail(movieId: Int): Either<Failure, MovieDetail?> {
        return remoteDataSource.getMovieDetail(movieId)
    }
}