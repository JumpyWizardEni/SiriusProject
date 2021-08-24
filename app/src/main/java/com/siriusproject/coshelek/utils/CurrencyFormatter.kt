package com.siriusproject.coshelek.utils

import java.math.BigDecimal
import java.text.NumberFormat
import java.util.*
import javax.inject.Inject

class CurrencyFormatter @Inject constructor() {
    private val formatter = NumberFormat.getCurrencyInstance(Locale("ru", "RU"))

    fun formatBigDecimal(number: BigDecimal): String {
        return formatter.format(number)
    }
}