package com.alirezamilani.persiandaterangepicker.calendar

import kotlin.math.ceil
import kotlin.math.floor

/**
 * Algorithms for converting *Julian days* to the *Persian calendar*, and vice versa
 * are adopted from [couprie.nl](http://casema.nl/couprie/calmath/persian/index.html)
 * written in VB. The algorithms is not exactly the same as its original. I've done some
 * minor changes in the sake of performances and corrected some bugs.
 *
 * @author Alireza Milani
 * @since 2.6.2
 */
class PersianCalendarUtils {

    companion object {

        /**
         * Converts a provided Persian **Shamsi** date to the Julian Day Number (i.e.
         * the number of days since January 1 in the year 4713 BC). Since the
         * Persian calendar is a highly regular calendar, converting to and from a
         * Julian Day Number is not as difficult as it looks. Basically it's a
         * mather of dividing, rounding and multiplying. This routine uses Julian
         * Day Number 1948321 as focal point, since that Julian Day Number
         * corresponds with 1 Farvardin (1) 1.
         */
        fun persianToJulian(year: Long, month: Int, day: Int): Long {
            return 365L * (ceil((year - 474.0), 2820.0) + 474L - 1L) +
                floor((682L * (ceil((year - 474.0), 2820.0) + 474L) - 110L) / 2816.0).toLong() +
                (PersianCalendarConstants.PERSIAN_EPOCH - 1L) +
                (1029983L * floor((year - 474L) / 2820.0).toLong()) +
                (if (month < 7) 31 * month else 30 * month + 6) + day
        }

        /**
         * Converts a provided Julian Day Number (i.e. the number of days since
         * January 1 in the year 4713 BC) to the Persian **Shamsi** date. Since the
         * Persian calendar is a highly regular calendar, converting to and from a
         * Julian Day Number is not as difficult as it looks. Basically it's a
         * mather of dividing, rounding and multiplying.
         */
        fun julianToPersian(julianDate: Long): Long {
            val persianEpochInJulian = julianDate - persianToJulian(475, 0, 1)
            val cYear = ceil(persianEpochInJulian.toDouble(), 1029983.0)
            val yCycle =
                if (cYear != 1029982L) floor((2816 * cYear + 1031337) / 1028522.0).toLong() else 2820L
            val year = 474L + 2820L * floor(persianEpochInJulian / 1029983.0).toLong() + yCycle
            val aux = 1L + julianDate - persianToJulian(year, 0, 1)
            val month = (
                if (aux > 186L)
                    ceil((aux - 6L).toDouble() / 30.0) - 1
                else
                    ceil(aux.toDouble() / 31.0) - 1
                ).toInt()
            val day = (julianDate - (persianToJulian(year, month, 1) - 1L)).toInt()
            return year shl 16 or (month shl 8).toLong() or day.toLong()
        }

        /**
         * Ceil function in original algorithm
         */
        fun ceil(x: Double, y: Double): Long = (x - y * floor(x / y)).toLong()

        /**
         * Calculate whether current year is Leap year in persian or not
         */
        fun isPersianLeapYear(persianYear: Int): Boolean {
            val ceil = ceil(
                (38.0 + (ceil((persianYear - 474).toDouble(), 2820.0) + 474)) * 682.0,
                2816.0
            )
            return ceil < 682
        }
    }
}