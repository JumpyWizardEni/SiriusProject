package com.siriusproject.coshelek.utils

import android.content.Context
import com.siriusproject.coshelek.R
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class DateTimeConverter(private val context: Context) {
    private val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
    private val dateFormatter = DateTimeFormatter.ofPattern("d MMMM")
    private val dateWithYearFormatter = DateTimeFormatter.ofPattern("d MMMM y")

    fun getCurrentDate(date: LocalDate): String {
        return when (date) {
            LocalDate.now() -> context.getString(R.string.today)
            LocalDate.now().minusDays(1) -> context.getString(R.string.yesterday)
            else -> {
                if (LocalDate.now().year == date.year) {
                    date.format(dateFormatter)
                } else {
                    date.format(dateWithYearFormatter)
                }
            }
        }
    }

    fun getCurrentTime(time: LocalTime): String {
        return time.format(timeFormatter)
    }
}