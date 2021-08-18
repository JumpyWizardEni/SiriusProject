package com.siriusproject.coshelek.data.model

import java.util.*

enum class TransactionType {
    Income, Expence
}


//TODO Все поля в принципе могут быть null
data class TransactionUiModel(
    val id: Int,
    val name: String,
    val category: CategoryUiModel,
    val type: TransactionType,
    val amount: Int,
    val currency: String,
    val date: Date
)