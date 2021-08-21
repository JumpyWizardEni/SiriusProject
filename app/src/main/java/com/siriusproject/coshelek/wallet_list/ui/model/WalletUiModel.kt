package com.siriusproject.coshelek.wallet_list.ui.model

import java.math.BigDecimal

data class WalletUiModel(
    val id: Int,
    val name: String,
    val balance: BigDecimal,
    val income: BigDecimal,
    val expense: BigDecimal,
    val currency: String,
    val visibility: Boolean,
    val limit: BigDecimal
)