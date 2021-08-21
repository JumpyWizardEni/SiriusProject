@file:UseSerializers(BigDecimalSerializer::class)

package com.siriusproject.coshelek.wallet_list.data.model

import com.siriusproject.coshelek.utils.BigDecimalSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.math.BigDecimal

@Serializable
data class WalletChangeBody(
    val name: String?,
    val currency: String?,
    val limit: BigDecimal?,
    val visibility: Boolean?
)