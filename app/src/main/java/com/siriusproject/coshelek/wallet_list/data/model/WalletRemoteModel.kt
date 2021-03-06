@file:UseSerializers(BigDecimalSerializer::class)

package com.siriusproject.coshelek.wallet_list.data.model

import com.siriusproject.coshelek.utils.BigDecimalSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.math.BigDecimal

@Serializable
data class WalletRemoteModel(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("balance")
    val balance: BigDecimal?,
    @SerialName("income")
    val income: BigDecimal?,
    @SerialName("expense")
    val expense: BigDecimal?,
    @SerialName("currency")
    val currency: String,
    @SerialName("visibility")
    val visibility: Boolean?,
    @SerialName("limit")
    val limit: BigDecimal,
    @SerialName("isLimitReached")
    val isLimitReached: Boolean?
)
