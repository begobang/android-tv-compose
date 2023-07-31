package com.nttdata.androidtv.data.services

import com.nttdata.androidtv.data.response.ApiResponse
import com.nttdata.androidtv.data.response.Movie
import com.nttdata.androidtv.data.response.Series
import com.nttdata.androidtv.data.response.SeriesDetail
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface SeriesService {

    @GET("3/discover/tv")
    fun getSeries(): Call<ApiResponse<Series>>

    @GET("3/tv/{tvId}/similar")
    fun getSimilarSeries(
        @Path("tvId") tvId: Int
    ): Call<ApiResponse<Series>>

    @GET("3/tv/{tvId}")
    fun getDetailSeries(
        @Path("tvId") tvId: Int
    ): Call<SeriesDetail>
}