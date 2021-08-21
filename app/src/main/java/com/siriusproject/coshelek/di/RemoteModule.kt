package com.siriusproject.coshelek.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.siriusproject.coshelek.utils.GoogleAuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {
    @OptIn(ExperimentalSerializationApi::class)
    @Provides
    @Singleton
    fun provideRetrofit(json: Json, client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .client(client)
            .baseUrl(
                "http://35.228.164.200:9090/"
            )
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()

    @Provides
    @Singleton
    fun provideJson(): Json = Json { ignoreUnknownKeys = true }

    @Provides
    @Singleton
    fun provideOkHttpClient(authRepos: GoogleAuthRepository): OkHttpClient =
        OkHttpClient.Builder().addInterceptor { chain ->
            val newRequest: Request = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer " + authRepos.token)
                .addHeader("email", authRepos.token)
                .build()
            chain.proceed(newRequest)
        }.build()

    @Provides
    @Singleton
    fun provideGoogleAuth() = GoogleAuthRepository()

//    @Provides
//    @Singleton
//    fun provideWalletService(retrofit: Retrofit): WalletService =
//        retrofit.create(WalletService::class.java)

}