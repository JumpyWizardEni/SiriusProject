package com.siriusproject.coshelek.wallet_information.data.model

import java.io.Serializable
import java.time.LocalDate
import java.time.LocalDateTime

enum class TransactionType {
    Income, Expence
}

sealed class TransactionListItem : Serializable

//TODO Все поля в принципе могут быть null

data class TransactionUiModel(
    var id: Int,
    var name: String,
    var category: CategoryUiModel,
    var type: TransactionType,
    var amount: Int,
    var currency: String,
    var date: LocalDateTime
) : Serializable, TransactionListItem()

data class TransactionHeaderModel(
    val date: LocalDate
) : TransactionListItem()