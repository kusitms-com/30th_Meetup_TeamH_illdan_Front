package com.poptato.core.util

import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter

object TimeFormatter {

    fun getToday(): String {
        val currentDate = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return currentDate.format(formatter)
    }

    fun getTodayMonthDay(): String {
        val currentDate = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("MM.dd")
        return currentDate.format(formatter)
    }
}