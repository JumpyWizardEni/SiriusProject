@file:UseSerializers(BigDecimalSerializer::class)

package com.siriusproject.coshelek.wallet_information.data.model

import com.siriusproject.coshelek.utils.BigDecimalSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.math.BigDecimal
import java.util.*

@Serializable
data class TransactionRemoteModel(

    @SerialName("id")
    val id: Int,

    @SerialName("name")
    val name: String,

    @SerialName("category")
    val category: String,

    @SerialName("ammount")
    val amount: BigDecimal,

    @SerialName("currency")
    val currency: String,

    @SerialName("type")
    val type: String,

    @SerialName("date")
    val date: Date,

    @SerialName("limit")
    val limit: BigDecimal

)