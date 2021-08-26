@file:UseSerializers(BigDecimalSerializer::class)

package com.siriusproject.coshelek.wallet_list.data.model

import com.siriusproject.coshelek.utils.BigDecimalSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.math.BigDecimal

@Serializable
data class CurrencyModel(
    @SerialName("name")
    val name: String,
    @SerialName("rate")
    val rate: BigDecimal,
    @SerialName("dynamics")
    val dynamic: String

)