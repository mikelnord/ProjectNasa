package com.gb.myapplication.util

import android.os.Build
import java.time.LocalDate
import java.time.format.DateTimeFormatter


fun convertDayToString(date: LocalDate): String {
    val formatter = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        DateTimeFormatter.ofPattern("yyyy-MM-dd")
    } else {
        TODO("VERSION.SDK_INT < O")
    }
    return date.format(formatter)
}