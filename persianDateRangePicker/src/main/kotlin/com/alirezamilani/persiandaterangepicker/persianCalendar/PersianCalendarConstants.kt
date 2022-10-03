package com.alirezamilani.persiandaterangepicker.persianCalendar

/**
 * @author Alireza Milani
 * @since 2.6.2
 */
class PersianCalendarConstants {

    companion object {
        /**
         * 00:00:00 UTC (Gregorian) Julian day 0,
         * 0 milliseconds since *1970-01-01*
         */
        const val MILLIS_JULIAN_EPOCH = -210866803200000L

        /**
         * Milliseconds of a day calculated by
         *
         * ``24L(hours) * 60L(minutes) * 60L(seconds) * 1000L(millis)``
         */
        const val MILLIS_OF_A_DAY = 86400000L

        /**
         * The JDN of 1 Farvardin 1; Equivalent to March 19, 622 A.D.
         */
        const val PERSIAN_EPOCH = 1948321L

        /**
         * List of persian month names
         */
        val PERSIAN_MONTH_NAMES = arrayOf(
            "\u0641\u0631\u0648\u0631\u062f\u06cc\u0646",
            "\u0627\u0631\u062f\u06cc\u0628\u0647\u0634\u062a",
            "\u062e\u0631\u062f\u0627\u062f",
            "\u062a\u06cc\u0631",
            "\u0645\u0631\u062f\u0627\u062f",
            "\u0634\u0647\u0631\u06cc\u0648\u0631",
            "\u0645\u0647\u0631",
            "\u0622\u0628\u0627\u0646",
            "\u0622\u0630\u0631",
            "\u062f\u06cc",
            "\u0628\u0647\u0645\u0646",
            "\u0627\u0633\u0641\u0646\u062f"
        )

        /**
         * List of persian week day names
         */
        val PERSIAN_WEEK_DAY_NAMES = arrayOf(
            "\u0634\u0646\u0628\u0647",
            "\u06cc\u06a9\u200c\u0634\u0646\u0628\u0647",
            "\u062f\u0648\u0634\u0646\u0628\u0647",
            "\u0633\u0647\u200c\u0634\u0646\u0628\u0647",
            "\u0686\u0647\u0627\u0631\u0634\u0646\u0628\u0647",
            "\u067e\u0646\u062c\u200c\u0634\u0646\u0628\u0647",
            "\u062c\u0645\u0639\u0647"
        )
    }
}
