package com.siriusproject.coshelek.wallet_information.data.model

import java.io.Serializable
import java.time.LocalDate
import java.time.LocalDateTime

enum class TransactionType {
    Income, Consumption
}

sealed class TransactionListItem : Serializable

//TODO Все поля в принципе могут быть null

data class TransactionUiModel(
    val id: Int,
    val name: String,
    val category: CategoryUiModel,
    val type: TransactionType,
    val amount: Int,
    val currency: String,
    val date: LocalDateTime
) : Serializable, TransactionListItem()

data class TransactionHeaderModel(
    val date: LocalDate
) : TransactionListItem()