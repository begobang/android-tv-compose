package com.nttdata.androidtv.data.di

import android.content.Context
import com.nttdata.androidtv.data.services.MoviesService
import com.nttdata.androidtv.data.services.SeriesService
import com.rocket.android.core.data.network.di.CoreDataNetworkProvider
import com.rocket.core.crashreporting.logger.CrashLogger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModuleDI {

    @Provides
    @Singleton
    fun provideMoviesService(restAdapter: Retrofit): MoviesService = restAdapter.create()

    @Provides
    @Singleton
    fun provideSeriesService(restAdapter: Retrofit): SeriesService = restAdapter.create()

    @Provides
    @ApiEndpoint
    fun provideApiEndPoint(): String = "https://api.themoviedb.org/"

    @Provides
    @ApiKey
    fun provideApiKey(): String = "caf6cc20b303897e95a1cf7b8cfc8912"

    @Provides
    fun provideRestAdapter(@ApiEndpoint apiEndPoint: String, okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(apiEndPoint)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideCrashLogger(provider: CoreDataNetworkProvider): CrashLogger {
        return provider.crashLogger
    }

    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        queryInterceptor: QueryInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(queryInterceptor)
        .build()

    @Provides
    @Singleton
    fun provideCoreDataNetworkProvider(@ApplicationContext context: Context): CoreDataNetworkProvider {
        return CoreDataNetworkProvider.getInstance(context)
    }





}

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class ApiKey

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class ApiEndpoint