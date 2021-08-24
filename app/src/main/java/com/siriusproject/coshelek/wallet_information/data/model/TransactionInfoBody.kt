@file:UseSerializers(BigDecimalSerializer::class, LocalDateTimeISO8601Serializer::class)

package com.siriusproject.coshelek.wallet_information.data.model

import com.siriusproject.coshelek.utils.BigDecimalSerializer
import com.siriusproject.coshelek.utils.LocalDateTimeISO8601Serializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.time.LocalDateTime

@Serializable
data class TransactionInfoBody(

    @SerialName("numberOfItems")
    val numberOfItems: Int,

    @SerialName("pageNumber")
    val pageNumber: Int,

    @SerialName("dateFrom")
    val dateFrom: LocalDateTime,

    @SerialName("dateTo")
    val dateTo: LocalDateTime

) 