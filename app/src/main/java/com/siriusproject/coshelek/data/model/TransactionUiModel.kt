package com.siriusproject.coshelek.data.model

import java.util.*

enum class TransactionType {
    Income, Expence
}


//TODO Все поля в принципе могут быть null
data class TransactionUiModel(
    var id: Int,
    var name: String,
    var category: CategoryUiModel,
    var type: TransactionType,
    var amount: Int,
    var currency: String,
    val date: Date
)