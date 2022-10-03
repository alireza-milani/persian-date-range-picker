package com.alirezamilani.persiandaterangepicker

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells.Fixed
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import com.alirezamilani.persiandaterangepicker.persianCalendar.PersianCalendar


/**
 * A date range picker body layout
 *
 * Date range picker allows users to select range of dates in full screen layout
 *
 * @param modifier The modifier to be applied to the [DateRangePicker].
 * @param initialDates start and end dates to be shown to the user when the [DateRangePicker] is first shown.
 * @param yearRange the range of years the user should be allowed to pick from
 * @param colors [DateRangePickerColors] that will be used to resolve the colors used for this
 * [DateRangePicker] in different states. See [DateRangePickerDefaults.colors].
 * @param title The title shown in the header
 * @param saveLabel The label shown in the confirm button
 * @param isRtl Specifies your layout is ltr or rtl
 * @param onCloseClick callback when the user close this layout
 * @param onConfirmClick callback when the user completes their input
 *
 * @author Alireza Milani
 * @since 1.0.0
 */
@Composable
fun DateRangePicker(
    modifier: Modifier = Modifier,
    initialDates: Pair<PersianCalendar, PersianCalendar>? = null,
    yearRange: IntRange = IntRange(1400, 1401),
    colors: DateRangePickerColors = DateRangePickerDefaults.colors(),
    title: String? = null,
    saveLabel: String? = null,
    isRtl: Boolean = true,
    onCloseClick: () -> Unit,
    onConfirmClick: (start: PersianCalendar, end: PersianCalendar) -> Unit
) {
    val state = rememberDateRangePickerState(initialDates, yearRange)

    val headerTitle = title ?: stringResource(id = R.string.picker_range_header_title)
    val headerSaveLabel = saveLabel ?: stringResource(id = R.string.picker_range_confirm)

    CompositionLocalProvider(
        LocalLayoutDirection provides if (isRtl) LayoutDirection.Rtl else LayoutDirection.Ltr
    ) {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HeaderDate(colors, state, headerSaveLabel, headerTitle, onCloseClick, onConfirmClick)

            LazyVerticalGrid(
                columns = Fixed(7),
                modifier = Modifier
                    .size(
                        width = DateRangePickerTokens.DaysOfWeekWidth,
                        height = DateRangePickerTokens.DayParentSize
                    ),
                userScrollEnabled = false
            ) {
                state.getDisplayNameOfDay().forEach { dayName ->
                    item {
                        Box(Modifier.size(DateRangePickerTokens.DayParentSize)) {
                            Text(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .wrapContentSize(Alignment.Center),
                                text = dayName,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            )
                        }
                    }
                }
            }

            val listState = rememberLazyListState(
                initialFirstVisibleItemIndex = state.position
            )

            LazyColumn(
                modifier = modifier.fillMaxWidth(),
                state = listState,
                contentPadding = PaddingValues(horizontal = DateRangePickerTokens.ContentPadding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(
                    items = state.months,
                    key = { it.timeInMillis },
                    contentType = { PersianCalendar::class }
                ) { calendar ->
                    Text(
                        modifier = Modifier
                            .fillParentMaxWidth()
                            .height(DateRangePickerTokens.MonthNameHeight)
                            .paddingFromBaseline(top = DateRangePickerTokens.MonthNameBaselinePadding)
                            .padding(horizontal = DateRangePickerTokens.MonthNameHorizontalPadding),
                        text = state.getLongName(calendar),
                        style = MaterialTheme.typography.titleSmall.copy(
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    )

                    CalendarMonth(
                        state = state,
                        colors = colors,
                        calendar = calendar
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun DateRangePickerPreview() {
    val start = PersianCalendar().apply {
        setPersianDate(1370, 1, 1)
    }
    val end = PersianCalendar().apply {
        setPersianDate(1370, 1, 4)
    }

    DateRangePicker(
        initialDates = start to end,
        onCloseClick = {}
    ) { _, _ -> }
}