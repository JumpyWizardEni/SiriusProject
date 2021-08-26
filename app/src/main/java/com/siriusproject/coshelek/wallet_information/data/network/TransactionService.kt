package com.siriusproject.coshelek.wallet_information.data.network

import com.siriusproject.coshelek.wallet_information.data.model.TransactionCreateBody
import com.siriusproject.coshelek.wallet_information.data.model.TransactionEditBody
import com.siriusproject.coshelek.wallet_information.data.model.TransactionInfo
import retrofit2.http.*
import java.time.LocalDateTime

interface TransactionService {

    @GET("wallet/{id}/transactions")
    suspend fun getTransactions(
        @Path("id") id: Int, @Query("numberOfItems") numberOfItems: Int,
        @Query("pageNumber") pageNumber: Int,
        @Query("dateFrom") dateFrom: LocalDateTime?,
        @Query("dateTo") dateTo: LocalDateTime?
    ): TransactionInfo

    @POST("transaction")
    suspend fun createTransaction(
        @Query("walletId") walletId: Int,
        @Body body: TransactionCreateBody
    )

    @PUT("transaction/{id}")
    suspend fun editingTransaction(@Path("id") id: Int, @Body body: TransactionEditBody)

    @DELETE("transaction/{id}")
    suspend fun deleteTransaction(@Path("id") id: Int)

}