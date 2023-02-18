package com.alirezamilani.persiandaterangepicker.calendar

/**
 * @author Alireza Milani
 * @since 2.6.2
 */
class PersianDateParser(private var dateString: String, private var delimiter: String = "/") {

    val persianDate: PersianCalendar
        get() {
            val tokens = splitDateString(dateString)
            val year = tokens[0].toInt()
            val month = tokens[1].toInt()
            val day = tokens[2].toInt()

            checkPersianDateValidation(year, month, day)

            val persianCalendar = PersianCalendar()
            persianCalendar.setPersianDate(year, month, day)

            return persianCalendar
        }

    private fun splitDateString(dateString: String): Array<String> {
        val tokens: Array<String> = dateString.split(delimiter).toTypedArray()
        if (tokens.size != 3) throw RuntimeException("Wrong date:$dateString is not a Persian Date or can not be parsed")
        return tokens
    }

    /**
     * Validate the given date
     */
    private fun checkPersianDateValidation(year: Int, month: Int, day: Int) {
        if (year < 1) throw java.lang.RuntimeException("Year is not valid")
        if (month < 1 || month > 12) throw java.lang.RuntimeException("Month is not valid")
        if (day < 1 || day > 31) throw java.lang.RuntimeException("Day is not valid")
        if (month > 6 && day == 31) throw java.lang.RuntimeException("Day is not valid")
        if (month == 12 && day == 30 && !PersianCalendarUtils.isPersianLeapYear(year)) throw java.lang.RuntimeException(
            "Day is not valid $year is not a leap year"
        )
    }
}