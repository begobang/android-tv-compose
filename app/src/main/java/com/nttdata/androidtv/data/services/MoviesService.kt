package com.nttdata.androidtv.data.services

import com.nttdata.androidtv.data.response.ApiResponse
import com.nttdata.androidtv.data.response.MovieDetail
import com.nttdata.androidtv.data.response.Movie
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesService {

    @GET("3/movie/now_playing")
    fun getNowPlaying(): Call<ApiResponse<Movie>>

    @GET("3/discover/movie")
    fun getMovies(): Call<ApiResponse<Movie>>

    @GET("3/movie/{movieId}/similar")
    fun getSimilarMovies(
        @Path("movieId") movieId: Int,
        @Query("page") page: Int = 1
    ): Call<ApiResponse<Movie>>

    @GET("3/movie/{movieId}")
    fun getDetailMovie(
        @Path("movieId") movieId: Int
    ): Call<MovieDetail>
}