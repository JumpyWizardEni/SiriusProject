package com.siriusproject.coshelek.utils

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.siriusproject.coshelek.wallet_information.ui.view.view_models.TransactionViewModel
import java.time.LocalDateTime

class DateTimeDialog {
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    fun createDialog(context: Context, viewModel: TransactionViewModel) {
        val localDateTime = LocalDateTime.now()
        val mYear = localDateTime.year
        val mMonth = localDateTime.monthValue
        val mDay = localDateTime.dayOfMonth
        val datePickerDialog = DatePickerDialog(
            context,
            android.R.style.Theme_DeviceDefault_Dialog_Alert,
            { view, year, monthOfYear, dayOfMonth ->
                var mHour = localDateTime.hour
                var mMinute = localDateTime.minute
                val timePickerDialog = TimePickerDialog(
                    context,
                    android.R.style.Theme_DeviceDefault_Dialog_Alert,
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