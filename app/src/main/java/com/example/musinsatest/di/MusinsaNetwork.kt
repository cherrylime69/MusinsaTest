package com.example.musinsatest.di

import com.example.musinsatest.data.network.MusinsaApi
import com.example.musinsatest.data.remote.MusinsaDataSource
import com.example.musinsatest.data.remote.MusinsaRemoteDataSource
import com.example.musinsatest.data.repository.HomeRepository
import com.example.musinsatest.data.repository.HomeRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object MusinsaNetwork {

    private const val BASE_URL = "https://meta.musinsa.com/"

    @Singleton
    @Provides
    fun provideInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BASIC
    }

    @Singleton
    @Provides
    fun provideOkhttpClient(logger: HttpLoggingInterceptor) =
        OkHttpClient.Builder()
            .addInterceptor(logger)
            .build()

    @Singleton
    @Provides
    fun provideMusinsaApi(client: OkHttpClient): MusinsaApi =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MusinsaApi::class.java)

    @Provides
    fun provideMusinsaDataSource(musinsaApi: MusinsaApi): MusinsaDataSource =
        MusinsaRemoteDataSource(musinsaApi)

    @Provides
    fun provideHomeRepository(musinsaDataSource: MusinsaDataSource): HomeRepository =
        HomeRepositoryImpl(musinsaDataSource)

}
