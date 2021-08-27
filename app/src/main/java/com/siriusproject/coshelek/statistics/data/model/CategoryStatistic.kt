@file:UseSerializers(BigDecimalSerializer::class, LocalDateTimeISO8601Serializer::class)

package com.siriusproject.coshelek.statistics.data.model


import com.siriusproject.coshelek.utils.BigDecimalSerializer
import com.siriusproject.coshelek.utils.LocalDateTimeISO8601Serializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.math.BigDecimal

@Serializable
class CategoryStatistic(
    val amount: BigDecimal,
    val category: String
)