package com.alirezamilani.persiandaterangepicker

import com.alirezamilani.persiandaterangepicker.IRSTDates.Companion
import com.alirezamilani.persiandaterangepicker.persianCalendar.PersianCalendar
import java.text.SimpleDateFormat
import java.util.Date

/**
 * Util methods for formatting date strings for use in [DateRangePicker].
 *
 * @author Alireza Milani
 * @since 1.0.0
 */
object DateStrings {

    fun getDateString(timeInMillis: Long, userDefinedDateFormat: SimpleDateFormat? = null): String {
        val currentCalendar = Companion.getTodayCalendar()
        val calendarDate = Companion.getCalendar()
        calendarDate.timeInMillis = timeInMillis

        if (userDefinedDateFormat != null) {
            val date = Date(timeInMillis)
            return userDefinedDateFormat.format(date)
        } else if (currentCalendar.persianYear == calendarDate.persianYear) {
            return getMonthDay(calendarDate)
        }

        return getYearMonthDay(calendarDate)
    }

    private fun getMonthDay(calendar: PersianCalendar): String =
        calendar.run { "$persianDay $persianMonthName" }

    private fun getYearMonthDay(calendar: PersianCalendar): String =
        calendar.run { "$persianDay $persianMonthName ,$persianYear" }

    fun getDateRangeString(start: Long?, end: Long?): Pair<String?, String?> {
        if (start == null && end == null) {
            return Pair(null, null)
        } else if (start == null) {
            return Pair(null, getDateString(end!!))
        } else if (end == null) {
            return Pair(getDateString(start), null)
        }

        val currentCalendar = Companion.getTodayCalendar()
        val startCalendar = Companion.getCalendar().apply {
            timeInMillis = start
        }
        val endCalendar = Companion.getCalendar().apply {
            timeInMillis = end
        }

        if (startCalendar.persianYear == endCalendar.persianYear) {
            return if (startCalendar.persianYear == currentCalendar.persianYear) {
                Pair(getMonthDay(startCalendar), getMonthDay(endCalendar))
            } else {
                Pair(getMonthDay(startCalendar), getYearMonthDay(endCalendar))
            }
        }

        return Pair(getYearMonthDay(startCalendar), getYearMonthDay(endCalendar))
    }
}