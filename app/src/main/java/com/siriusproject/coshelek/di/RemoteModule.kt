package com.siriusproject.coshelek.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.siriusproject.coshelek.BuildConfig
import com.siriusproject.coshelek.categories_info.data.remote.CategoriesApi
import com.siriusproject.coshelek.utils.GoogleAuthRepository
import com.siriusproject.coshelek.wallet_information.data.network.TransactionService
import com.siriusproject.coshelek.wallet_list.data.remote.WalletService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {
    const val BASE_URL = "http://35.228.164.200:9090/"
    @OptIn(ExperimentalSerializationApi::class)
    @Provides
    @Singleton
    fun provideRetrofit(json: Json, client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .client(client)
            .baseUrl(
                BASE_URL
            )
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()

    @Provides
    @Singleton
    fun provideJson(): Json = Json { ignoreUnknownKeys = true }
    val x = "Bearer aaa12332"
    val y = "email12"

    @Provides
    @Singleton
    fun provideOkHttpClient(authRepos: GoogleAuthRepository): OkHttpClient {
        val builder = OkHttpClient.Builder().addInterceptor { chain ->
            val newRequest: Request = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer " + authRepos.token)
                .addHeader("email", authRepos.email)
                .build()
            chain.proceed(newRequest)
        }
        if (BuildConfig.DEBUG) {
            builder.addNetworkInterceptor(HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            })
        }
        return builder.build()
    }

    @Provides
    @Singleton
    fun provideGoogleAuth() = GoogleAuthRepository()

    @Provides
    @Singleton
    fun provideWalletService(retrofit: Retrofit): WalletService =
        retrofit.create(WalletService::class.java)

    @Provides
    @Singleton
    fun provideCategoriesRemoteSource(retrofit: Retrofit): CategoriesApi =
        retrofit.create(CategoriesApi::class.java)

    @Provides
    @Singleton
    fun provideTransactionService(retrofit: Retrofit): TransactionService =
        retrofit.create(TransactionService::class.java)

}