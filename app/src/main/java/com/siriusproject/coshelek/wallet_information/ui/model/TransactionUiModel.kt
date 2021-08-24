package com.siriusproject.coshelek.wallet_information.ui.model

import java.io.Serializable
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime

enum class TransactionType {
    Income, Expence
}

sealed class TransactionListItem : Serializable

//TODO Все поля в принципе могут быть null

data class TransactionUiModel(
    val id: Int,
    val name: String,
    val category: String,
    val type: TransactionType,
    val amount: BigDecimal,
    val currency: String,
    val date: LocalDateTime
) : Serializable, TransactionListItem()

data class TransactionHeaderModel(
    val date: LocalDate
) : TransactionListItem()