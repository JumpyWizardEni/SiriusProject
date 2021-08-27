package com.siriusproject.coshelek.statistics.data.remote

import com.siriusproject.coshelek.statistics.data.model.CategoryStatistic
import retrofit2.http.GET

interface StatisticsRemote {
//    @GET("wallets")
//    suspend fun getWalletsList(): List<WalletRemoteModel>

//    @GET("wallet/{id}")
//    suspend fun getWalletInfo(@Path("id") id: Int): WalletRemoteModel

    @GET("categories/expense")
    suspend fun getCategoriesStatistics(): List<CategoryStatistic>

//    @PUT("wallet/{id}")
//    suspend fun changeWallet(@Path("id") id: Int, @Body body: WalletChangeBody)
//
//    @DELETE("wallet/{id}")
//    suspend fun deleteWallet(@Path("id") id: Int)
//
//    @GET("currencies")
//    suspend fun getCurrencies(): List<CurrencyModel>

}