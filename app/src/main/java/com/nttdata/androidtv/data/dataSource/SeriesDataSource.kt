package com.nttdata.androidtv.data.dataSource

import com.nttdata.androidtv.data.response.Series
import com.nttdata.androidtv.data.response.SeriesDetail
import com.nttdata.androidtv.data.services.SeriesService
import com.rocket.android.core.data.network.datasource.BaseNetworkDatasource
import com.rocket.core.crashreporting.logger.CrashLogger
import com.rocket.core.domain.error.Failure
import com.rocket.core.domain.functional.Either
import javax.inject.Inject

class SeriesDataSource @Inject constructor(
    private val service: SeriesService,
    crashLogger: CrashLogger
): BaseNetworkDatasource(crashLogger) {

    fun getSeries(): Either<Failure, List<Series>?> {
        return requestGenericApi(
            call = { service.getSeries() },
            parserSuccess = { it?.results }
        )
    }

    fun getSimilarSeries(tvId: Int): Either<Failure, List<Series>?> {
        return requestGenericApi(
            call = { service.getSimilarSeries(tvId) },
            parserSuccess = { it?.results }
        )
    }

    fun getSeriesDetail(tvId: Int): Either<Failure, SeriesDetail?> {
        return requestGenericApi(
            call = { service.getDetailSeries(tvId) },
            parserSuccess = { it }
        )
    }
}