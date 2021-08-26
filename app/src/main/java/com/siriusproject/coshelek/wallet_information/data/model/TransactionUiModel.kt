package com.siriusproject.coshelek.wallet_information.data.model

import com.siriusproject.coshelek.categories_info.data.model.CategoryUiModel
import java.io.Serializable
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime

enum class TransactionType {
    Income {
        override fun toString(): String {
            return "INCOME"
        }
    },
    Expense {
        override fun toString(): String {
            return "EXPENSE"
        }
    }
}

sealed class TransactionListItem : Serializable

//TODO Все поля в принципе могут быть null

data class TransactionUiModel(
    val id: Int,
    val category: CategoryUiModel,
    val type: TransactionType,
    val amount: BigDecimal,
    val currency: String,
    val date: LocalDateTime
) : Serializable, TransactionListItem()

data class TransactionHeaderModel(
    val date: LocalDate
) : TransactionListItem()

object TransactionProgressBarModel : TransactionListItem()