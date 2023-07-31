package com.nttdata.androidtv.data.repository

import com.nttdata.androidtv.data.dataSource.SeriesDataSource
import com.nttdata.androidtv.data.response.Series
import com.nttdata.androidtv.data.response.SeriesDetail
import com.rocket.core.domain.error.Failure
import com.rocket.core.domain.functional.Either
import javax.inject.Inject

class SeriesRepository @Inject constructor(private val remoteDataSource: SeriesDataSource) {

    fun getSeries(): Either<Failure, List<Series>?> {
        return remoteDataSource.getSeries()
    }

    suspend fun getSimilarMovies(tvId: Int): Either<Failure, List<Series>?> {
        return remoteDataSource.getSimilarSeries(tvId)
    }

    suspend fun getSeriesDetail(tvId: Int): Either<Failure, SeriesDetail?> {
        return remoteDataSource.getSeriesDetail(tvId)
    }
}