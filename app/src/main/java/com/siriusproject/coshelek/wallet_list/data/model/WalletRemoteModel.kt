package com.siriusproject.coshelek.wallet_list.data.model

import java.math.BigDecimal

data class WalletRemoteModel(
    val id: Int,
    val name: String,
    val balance: BigDecimal,
    val income: BigDecimal,
    val expense: BigDecimal,
    val currency: String,
    val visibility: Boolean,
    val limit: BigDecimal
)
