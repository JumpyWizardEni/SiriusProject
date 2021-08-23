package com.siriusproject.coshelek.wallet_list.data.remote

import com.siriusproject.coshelek.wallet_list.data.model.WalletChangeBody
import com.siriusproject.coshelek.wallet_list.data.model.WalletCreateBody
import com.siriusproject.coshelek.wallet_list.data.model.WalletInfoRemoteModel
import com.siriusproject.coshelek.wallet_list.data.model.WalletRemoteModel
import retrofit2.Response
import retrofit2.http.*

interface WalletService {
    @GET("wallets")
    suspend fun getWalletsList(): Response<List<WalletRemoteModel>>

    @GET("wallet/{id}")
    suspend fun getWalletInfo(@Path("id") id: Int): WalletInfoRemoteModel

    @POST("wallet")
    suspend fun createWallet(@Body body: WalletCreateBody)

    @PUT("wallet/{id}")
    suspend fun changeWallet(@Path("id") id: Int, @Body body: WalletChangeBody)

    @DELETE("wallet/{id}")
    suspend fun deleteWallet(@Path("id") id: Int)

}