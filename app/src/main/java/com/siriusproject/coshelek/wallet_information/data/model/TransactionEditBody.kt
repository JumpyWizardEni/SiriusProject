@file:UseSerializers(BigDecimalSerializer::class, LocalDateTimeISO8601Serializer::class)

package com.siriusproject.coshelek.wallet_information.data.model

import com.siriusproject.coshelek.utils.BigDecimalSerializer
import com.siriusproject.coshelek.utils.LocalDateTimeISO8601Serializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.math.BigDecimal
import java.time.LocalDateTime

@Serializable
data class TransactionEditBody(

    @SerialName("amount")
    val amount: BigDecimal,

    @SerialName("type")
    val type: TransactionType,

    @SerialName("category")
    val category: String,

    @SerialName("currency")
    val currency: String,

    @SerialName("date")
    val date: LocalDateTime

) 