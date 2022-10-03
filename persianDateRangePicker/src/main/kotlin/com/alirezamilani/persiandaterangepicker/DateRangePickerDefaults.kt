package com.alirezamilani.persiandaterangepicker

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItemColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color

/**
 * Defaults used in [DateRangePicker].
 */
object DateRangePickerDefaults {

    /**
     * Creates a [NavigationDrawerItemColors] with the provided colors according to the Material
     * specification.
     *
     * @param activeDateContainerColor the color to use for the background of the date when is active
     * @param inactiveDateContainerColor the color to use for the background of the date when is inactive
     * @param activeDateTextColor the color to use for the text label when the date is active.
     * @param inactiveDateTextColor the color to use for the text label when the date is inactive.
     * @param rangeDateContainerColor the color to use for the background of the dates are between start and end dates.
     *
     * @return the resulting [DateRangePickerColors] used for [DateRangePicker]
     */
    @Composable
    fun colors(
        activeDateContainerColor: Color = MaterialTheme.colorScheme.primary,
        inactiveDateContainerColor: Color = Color.Transparent,
        activeDateTextColor: Color = MaterialTheme.colorScheme.onPrimary,
        inactiveDateTextColor: Color = MaterialTheme.colorScheme.onSurface,
        rangeDateContainerColor: Color = activeDateContainerColor.copy(alpha = .12f),
        headerContainerColor: Color = MaterialTheme.colorScheme.primary,
        headerContentColor: Color = MaterialTheme.colorScheme.onPrimary
    ): DateRangePickerColors = remember(
        headerContainerColor,
        headerContentColor,
        rangeDateContainerColor,
        activeDateContainerColor,
        inactiveDateContainerColor,
        activeDateTextColor,
        inactiveDateTextColor
    ) {
        DefaultDateRangePickerColors(
            headerContainerColor,
            headerContentColor,
            rangeDateContainerColor,
            activeDateContainerColor,
            inactiveDateContainerColor,
            activeDateTextColor,
            inactiveDateTextColor
        )
    }
}