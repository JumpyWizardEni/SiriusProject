package com.siriusproject.coshelek.wallet_information.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TransactionInfo(

    @SerialName("transactionList")
    val transactionList: List<TransactionRemoteModel>,

    @SerialName("totalNumOfItems")
    val totalNumOfItems: Long

)