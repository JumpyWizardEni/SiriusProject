package com.siriusproject.coshelek.wallet_information.data.model

data class CategoryUiModel(
    val id: Int,
    val name: String,
    val type: TransactionType,
    val picture: Int,
    val color: Int,
)