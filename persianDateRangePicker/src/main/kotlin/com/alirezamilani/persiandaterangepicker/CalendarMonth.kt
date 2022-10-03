package com.alirezamilani.persiandaterangepicker

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells.Fixed
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alirezamilani.persiandaterangepicker.persianCalendar.PersianCalendar

/**
 * A layout to show days of month
 *
 * @author Alireza Milani
 * @since 1.0.0
 */
@Composable
fun CalendarMonth(
    modifier: Modifier = Modifier,
    state: DateRangePickerState = rememberDateRangePickerState(yearRange = IntRange(1400, 1401)),
    colors: DateRangePickerColors = DateRangePickerDefaults.colors(),
    calendar: PersianCalendar
) {
    val (firstDay, numDays) = remember { state.getDates(calendar) }
    val datesList = remember { IntRange(1, numDays).toList() }
    val heightFactor = remember { state.getMaximumWeeks(firstDay, numDays) }

    LazyVerticalGrid(
        columns = Fixed(7),
        modifier = modifier.size(
            width = DateRangePickerTokens.DaysOfWeekWidth,
            height = (heightFactor * 48).dp
        ),
        userScrollEnabled = false
    ) {

        for (x in 0 until firstDay) {
            item { Box(Modifier.size(DateRangePickerTokens.DayParentSize)) }
        }

        items(datesList) { date ->
            val currentDate = (calendar.clone() as PersianCalendar).apply {
                setPersianDay(date)
            }
            val isSelected = remember(state.selectedStartDate, state.selectedEndDate) {
                state.isSelected(currentDate.timeInMillis)
            }

            val isRangeFill = remember(state.selectedStartDate, state.selectedEndDate) {
                state.isInRange(currentDate.timeInMillis)
            }

            val dayType = remember(state.selectedStartDate, state.selectedEndDate) {
                state.getDayType(currentDate.timeInMillis)
            }

            CalendarDay(
                date = date,
                selected = isSelected,
                dayType = dayType,
                isRangeFill = isRangeFill,
                colors = colors,
                onClick = { state.select(currentDate.timeInMillis) }
            )
        }
    }
}

@Preview
@Composable
fun CalendarMonthPreview() {
    val calendar = PersianCalendar().apply {
        setPersianYear(1370)
        setPersianMonth(4)
    }
    CalendarMonth(calendar = calendar)
}