package com.siriusproject.coshelek.wallet_list.data.remote

import com.siriusproject.coshelek.wallet_list.data.model.WalletRemoteModel
import retrofit2.http.GET

interface WalletService {
    //TODO
    @GET("")
    suspend fun getWalletsList(): List<WalletRemoteModel>
}