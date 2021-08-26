package com.siriusproject.coshelek.utils

import com.siriusproject.coshelek.wallet_information.data.model.TransactionType
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.*
import javax.inject.Inject

class CurrencyFormatter @Inject constructor() {

    private fun getLocale(currency: String): Locale {
        return when (currency) {
            "RUB" -> Locale("ru", "RU")
            "USD" -> Locale("en", "US")
            else -> Locale("es", "ES")
        }
    }

    fun formatBigDecimal(number: BigDecimal, currency: String): String {
        val formatter = NumberFormat.getCurrencyInstance(getLocale(currency))
        return formatter.format(number)
    }

    fun formatBigDecimalWithSign(
        number: BigDecimal,
        type: TransactionType,
        currency: String
    ): String {
        val formatter = NumberFormat.getCurrencyInstance(getLocale(currency))
        return when (type) {
            TransactionType.Expense ->
                "-" + formatter.format(number)
            else ->
                formatter.format(number)
        }
    }
}