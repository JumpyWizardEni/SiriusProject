package com.siriusproject.coshelek.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.siriusproject.coshelek.utils.GoogleAuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {
    @Provides
    @Singleton
    //TODO URL
    fun provideRetrofit(gson: Gson, client: OkHttpClient): Retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl("")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    @Singleton
    fun provideGsonFactory(): Gson = GsonBuilder().setLenient().create()

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

}