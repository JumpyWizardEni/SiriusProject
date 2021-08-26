package com.siriusproject.coshelek.utils

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import com.siriusproject.coshelek.wallet_information.ui.view.view_models.TransactionViewModel
import java.time.LocalDateTime

class DateTimeDialog {
    fun createDialog(context: Context, viewModel: TransactionViewModel) {
        val c = LocalDateTime.now()
        val mYear = c.year
        val mMonth = c.monthValue
        val mDay = c.dayOfMonth
        val datePickerDialog = DatePickerDialog(
            context,
            { view, year, monthOfYear, dayOfMonth ->
                var mHour = c.hour
                var mMinute = c.minute
                val timePickerDialog = TimePickerDialog(
                    context,
                    TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                        viewModel.pushDateTime(
                            LocalDateTime.of(
                                year,
                                monthOfYear,
                                dayOfMonth,
                                hourOfDay,
                                minute
                            )
                        )
                    }, mHour, mMinute, false
                )
                timePickerDialog.show()
            }, mYear, mMonth, mDay
        )
        datePickerDialog.show()
    }
}