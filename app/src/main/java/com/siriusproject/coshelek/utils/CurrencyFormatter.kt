package com.siriusproject.coshelek.utils

import com.siriusproject.coshelek.wallet_information.data.model.TransactionType
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.*
import javax.inject.Inject

class CurrencyFormatter @Inject constructor() {
    private val formatter = NumberFormat.getCurrencyInstance(Locale("ru", "RU"))

    fun formatBigDecimal(number: BigDecimal): String {
        return formatter.format(number)
    }

    fun formatBigDecimalWithSign(number: BigDecimal, type: TransactionType): String {
        return when (type) {
            TransactionType.Expense ->
                "-" + formatter.format(number)
            else ->
                formatter.format(number)
        }
    }
}