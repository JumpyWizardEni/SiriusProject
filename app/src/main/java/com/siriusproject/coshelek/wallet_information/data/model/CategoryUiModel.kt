package com.siriusproject.coshelek.wallet_information.data.model

import com.siriusproject.coshelek.wallet_information.ui.model.TransactionType

data class CategoryUiModel(
    val id: Int,
    val name: String,
    val type: TransactionType,
    val picture: Int,
    val color: Int
)