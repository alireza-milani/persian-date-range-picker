package com.alirezamilani.persiandaterangepicker

import com.alirezamilani.persiandaterangepicker.calendar.PersianCalendar
import java.util.*

/**
 * Utility class for common operations on timezones, calendars, date formats, and longs representing
 * time in milliseconds according to [PersianCalendar].
 *
 * @author Alireza Milani
 * @since 1.0.0
 */
class IRSTDates {

    companion object {
        private val timeZone: TimeZone = TimeZone.getTimeZone("IRST")

        /**
         * Returns a [PersianCalendar] object in IRST time zone representing the first moment of current date.
         */
        fun getTodayCalendar() = PersianCalendar().also {
            it.set(Calendar.HOUR_OF_DAY, 0)
            it.set(Calendar.MINUTE, 0)
            it.set(Calendar.SECOND, 0)
            it.set(Calendar.MILLISECOND, 0)
            it.timeZone = timeZone
        }

        /**
         * Returns a [PersianCalendar] object in IRST time zone representing the moment in input [PersianCalendar] object.
         * An empty [PersianCalendar] object in IRST will be return if input is null.
         *
         * @param rawCalendar the Calendar object representing the moment to process.
         * @return A [PersianCalendar] object in IRST time zone.
         */
        fun getCalendarOf(rawCalendar: PersianCalendar?): PersianCalendar {
            val calendar = PersianCalendar()

            if (rawCalendar == null) {
                calendar[Calendar.HOUR_OF_DAY] = 0
                calendar[Calendar.MINUTE] = 0
                calendar[Calendar.SECOND] = 0
                calendar[Calendar.MILLISECOND] = 0
            } else {
                calendar.timeInMillis = rawCalendar.timeInMillis
            }
            return calendar
        }

        /**
         * Returns an empty [PersianCalendar] in IRST time zone.
         */
        fun getCalendar(): PersianCalendar = getCalendarOf(null)

        fun getCalendar(year: Int, month: Int, day: Int): PersianCalendar {
            val calendar = PersianCalendar().apply {
                setPersianDate(
                    year = year,
                    month = month,
                    day = day
                )
            }

            return getCalendarOf(calendar)
        }

        /**
         * Returns a [PersianCalendar] object in IRST time zone representing the start of day in IRST represented in
         * the input [PersianCalendar] object, i.e., the time (fields smaller than a day) is stripped based on the
         * IRST time zone.
         *
         * @param rawCalendar the [PersianCalendar] object representing the moment to process.
         * @return A [PersianCalendar] object representing the start of day in IRST time zone.
         */
        private fun getDayCopy(rawCalendar: PersianCalendar?): PersianCalendar {
            val rawCalendarInIrst = getCalendarOf(rawCalendar)
            val irstCalendar = getCalendar()
            irstCalendar[rawCalendarInIrst[Calendar.YEAR], rawCalendarInIrst[Calendar.MONTH]] =
                rawCalendarInIrst[Calendar.DAY_OF_MONTH]
            return irstCalendar
        }

        /**
         * Strips all information from the time in milliseconds at granularities more specific than day of
         * the month.
         *
         * @param rawDate A long representing the time as IRST milliseconds from the epoch
         * @return A canonical long representing the time as IRST milliseconds for the represented day.
         */
        fun canonicalYearMonthDay(rawDate: Long): Long {
            val rawCalendar = getCalendar()
            rawCalendar.timeInMillis = rawDate
            val sanitizedStartItem = getDayCopy(rawCalendar)
            return sanitizedStartItem.timeInMillis
        }
    }
}