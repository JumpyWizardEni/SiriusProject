@file:UseSerializers(BigDecimalSerializer::class)

package com.siriusproject.coshelek.wallet_information.data.model

import com.siriusproject.coshelek.utils.BigDecimalSerializer
import com.siriusproject.coshelek.wallet_information.ui.model.TransactionType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.math.BigDecimal

@Serializable
data class TransactionCreateBody(

    @SerialName("amount")
    val amount: BigDecimal,

    @SerialName("type")
    val type: TransactionType,

    @SerialName("category")
    val category: String,

    @SerialName("currency")
    val currency: String,

//    @SerialName("date")
//    val date: Date

)