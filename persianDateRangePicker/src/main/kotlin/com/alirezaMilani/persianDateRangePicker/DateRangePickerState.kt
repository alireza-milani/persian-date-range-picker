package com.alirezaMilani.persianDateRangePicker

import android.content.res.Resources
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.alirezaMilani.persianDateRangePicker.persianCalendar.PersianCalendar
import java.util.*

/**
 * Responsible for holding state related to [DateRangePicker] and containing UI related logic
 *
 * @author Alireza Milani
 * @since 1.0.0
 */
class DateRangePickerState(
    private val initialDates: Pair<Long?, Long?>? = null,
    yearRange: IntRange,
    private val resources: Resources,
) {
    var selectedStartDate: Long? by mutableStateOf(null)
    var selectedEndDate: Long? by mutableStateOf(null)

    var position: Int by mutableStateOf(0)

    val months = mutableListOf<PersianCalendar>()

    init {
        if (initialDates != null) {
            setSelection(initialDates)
        }
        getMonths(yearRange)
    }

    /**
     * Creates list of months according to year range
     */
    private fun getMonths(yearRange: IntRange) {
        val calendar = if (initialDates?.first != null) {
            IRSTDates.getCalendarOf(PersianCalendar(initialDates.first!!))
        } else {
            IRSTDates.getTodayCalendar()
        }

        for (i in 0 until (yearRange.last - yearRange.first + 1) * 12) {
            val currentCalendar = IRSTDates.getCalendar(
                year = yearRange.first + (i / 12), month = i % 12 + 1, day = 1
            )
            if (currentCalendar.persianYear == calendar.persianYear && currentCalendar.persianMonth == calendar.persianMonth) {
                position = i
            }

            months.add(currentCalendar)
        }
    }

    private fun setSelection(selection: Pair<Long?, Long?>) {
        if (selection.first != null && selection.second != null) {
            require(isValidRange(selection.first!!, selection.second!!))
        }

        selectedStartDate = selection.first?.let {
            IRSTDates.canonicalYearMonthDay(it)
        }

        selectedEndDate = selection.second?.let {
            IRSTDates.canonicalYearMonthDay(it)
        }
    }

    fun select(selection: Long) {
        if (selectedStartDate == null) {
            selectedStartDate = selection
        } else if (selectedEndDate == null && isValidRange(selectedStartDate!!, selection)) {
            selectedEndDate = selection
        } else {
            selectedEndDate = null
            selectedStartDate = selection
        }
    }

    fun isSelectionComplete(): Boolean {
        return selectedStartDate != null && selectedEndDate != null && isValidRange(
            selectedStartDate!!, selectedEndDate!!
        )
    }

    private fun isValidRange(start: Long, end: Long): Boolean = start <= end

    private fun getSelectionDays(): Collection<Long> {
        val selections = arrayListOf<Long>()
        selectedStartDate?.let {
            selections.add(it)
        }
        selectedEndDate?.let {
            selections.add(it)
        }

        return selections
    }

    fun isSelected(date: Long): Boolean {
        for (selectionDay in getSelectionDays()) {
            if (IRSTDates.canonicalYearMonthDay(date) == IRSTDates.canonicalYearMonthDay(
                    selectionDay
                )
            ) {
                return true
            }
        }
        return false
    }

    fun isInRange(date: Long): Boolean {
        if (selectedStartDate == null || selectedEndDate == null) {
            return false
        }

        if (IRSTDates.canonicalYearMonthDay(date) > IRSTDates.canonicalYearMonthDay(
                selectedStartDate!!
            ) && IRSTDates.canonicalYearMonthDay(date) < IRSTDates.canonicalYearMonthDay(
                selectedEndDate!!
            )
        ) {
            return true
        }

        return false
    }

    fun getDayType(date: Long): DayType {
        var dayType: DayType = DayType.Day

        val currentDate = IRSTDates.canonicalYearMonthDay(date)

        if (currentDate == IRSTDates.getTodayCalendar().timeInMillis) {
            dayType = DayType.Today
        }

        if (selectedStartDate != null && selectedEndDate != null) {
            if (IRSTDates.canonicalYearMonthDay(selectedStartDate!!) != IRSTDates.canonicalYearMonthDay(
                    selectedEndDate!!
                )
            ) {
                when (currentDate) {
                    IRSTDates.canonicalYearMonthDay(selectedStartDate!!) -> dayType = DayType.Start
                    IRSTDates.canonicalYearMonthDay(selectedEndDate!!) -> dayType = DayType.End
                }
            }
        } else if (selectedStartDate == null || selectedEndDate == null) {
            if (currentDate != IRSTDates.getTodayCalendar().timeInMillis) {
                dayType = DayType.Day
            }
        }

        return dayType
    }

    /**
     * @return first day of [calendar] and number of days in this month
     */
    fun getDates(calendar: PersianCalendar): Pair<Int, Int> {
        val numDays = calendar.getMonthLength()

        val firstDay = calendar.run {
            setPersianDay(1)
            val day = calendar[Calendar.DAY_OF_WEEK]
            // When day is Saturday, the above line returns value equal 7 and this isn't be useful,
            // must be convert to 0
            if (day == 7) 0 else day
        }

        return Pair(firstDay, numDays)
    }

    fun getLongName(date: PersianCalendar): String {
        return "${date.persianMonthName} ${date.persianYear}"
    }

    /**
     * Creates abbreviation of persian days of week
     */
    fun getDisplayNameOfDay(): List<String> = listOf("ش", "ی", "د", "س", "چ", "پ", "ج")

    /**
     * Shows text in calendar's header according to selected start and end dates
     */
    fun updateHeader(): String {
        if (selectedStartDate == null && selectedEndDate == null) {
            return resources.getString(R.string.picker_range_header_unselected)
        }

        if (selectedEndDate == null) {
            return resources.getString(
                R.string.picker_range_header_only_start_selected,
                DateStrings.getDateString(selectedStartDate!!)
            )
        }

        if (selectedStartDate == null) {
            return resources.getString(
                R.string.picker_range_header_only_end_selected,
                DateStrings.getDateString(selectedEndDate!!)
            )
        }

        val dateRangeString = DateStrings.getDateRangeString(selectedStartDate!!, selectedEndDate!!)
        return resources.getString(
            R.string.picker_range_header_selected, dateRangeString.first, dateRangeString.second
        )
    }

    /**
     * @return number of week in month
     */
    fun getMaximumWeeks(firstDay: Int, numDays: Int): Int {
        return if ((firstDay == 6 && numDays >= 30) || (firstDay == 5 && numDays == 31)) 6 else 5
    }
}

/**
 * Remembers and creates an instance of [DateRangePickerState]
 *
 * @author Alireza Milani
 * @since 1.0.0
 */
@Composable
fun rememberDateRangePickerState(
    initialDates: Pair<PersianCalendar, PersianCalendar>? = null,
    yearRange: IntRange,
    resources: Resources = LocalContext.current.resources
): DateRangePickerState {
    val startEndDates = initialDates?.run {
        Pair(first.timeInMillis, second.timeInMillis)
    }

    return remember {
        DateRangePickerState(startEndDates, yearRange, resources)
    }
}