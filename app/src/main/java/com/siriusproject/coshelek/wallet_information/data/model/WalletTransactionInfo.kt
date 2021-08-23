package com.siriusproject.coshelek.wallet_information.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WalletTransactionInfo(

    val transactionsRemoteModel: List<TransactionRemoteModel>,

    @SerialName("totalNumOfItems")
    val totalNumOfItems: Long

)