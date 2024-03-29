package com.tilikki.movipedia.util

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateParser {
    const val ISO_DATE_TIME_INSTANT: String = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"

    fun convertDateFormat(
        inputDate: String,
        inputDateFormat: String,
        outputDateFormat: String,
        locale: Locale = Locale.ROOT
    ): String {
        val inputFormatter = SimpleDateFormat(inputDateFormat, locale)
        val outputFormatter = SimpleDateFormat(outputDateFormat, locale)
        val date: Date = inputFormatter.parse(inputDate)
            ?: throw ParseException("Unparseable date: \"$inputDate\"", 0)
        return outputFormatter.format(date)
    }

    fun convertDateFormat(
        inputDate: String,
        inputDateFormat: String,
        outputDateStyle: Int,
        locale: Locale = Locale.UK
    ): String {
        val inputFormatter = SimpleDateFormat(inputDateFormat, locale)
        val outputFormatter = SimpleDateFormat.getDateInstance(outputDateStyle, locale)
        val date: Date = inputFormatter.parse(inputDate)
            ?: throw ParseException("Unparseable date: \"$inputDate\"", 0)
        return outputFormatter.format(date)
    }
}