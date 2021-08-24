package com.siriusproject.coshelek.wallet_information.data.network

import com.siriusproject.coshelek.wallet_information.data.model.TransactionCreateBody
import com.siriusproject.coshelek.wallet_information.data.model.TransactionEditBody
import com.siriusproject.coshelek.wallet_information.data.model.TransactionInfo
import com.siriusproject.coshelek.wallet_information.data.model.TransactionInfoBody
import retrofit2.http.*

interface TransactionService {

    @GET("/wallet/{id}/transactions")
    suspend fun getTransactions(
        @Path("id") id: Int,
        @Body body: TransactionInfoBody
    ): TransactionInfo

    @POST("/transaction/{walletId}")
    suspend fun createTransaction(
        @Path("walletId") walletId: Int,
        @Body body: TransactionCreateBody
    )

    @PUT("/transaction/{id}")
    suspend fun editingTransaction(@Path("id") id: Int, @Body body: TransactionEditBody)

    @DELETE("/transaction/{id}")
    suspend fun deleteTransaction(@Path("id") id: Int)

}