package com.alirezamilani.persiandaterangepicker

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.graphics.Color

/** Represents the colors of the various elements of a date range picker in different states. */
@Stable
interface DateRangePickerColors {

    /**
     * Represents the container color for header.
     */
    val headerContainerColor: Color

    /**
     * Represents the content color for header.
     */
    val headerContentColor: Color

    /**
     * Represents the container color for dates between start and end selected dates.
     */
    val rangeDateContainerColor: Color

    /**
     * Represents the container color for this date, depending on whether it is [active].
     *
     * @param active whether the item is selected
     */
    @Composable
    fun dateContainerColor(active: Boolean): State<Color>

    /**
     * Represents the text color for this date, depending on whether it is [active].
     *
     * @param active whether the date is selected
     */
    @Composable
    fun dateTextColor(active: Boolean): State<Color>
}

internal class DefaultDateRangePickerColors(
    override val headerContainerColor: Color,
    override val headerContentColor: Color,
    override val rangeDateContainerColor: Color,
    private val activeDateContainerColor: Color,
    private val inactiveDateContainerColor: Color,
    private val activeDateTextColor: Color,
    private val inactiveDateTextColor: Color
) : DateRangePickerColors {

    @Composable
    override fun dateContainerColor(active: Boolean): State<Color> {
        return rememberUpdatedState(if (active) activeDateContainerColor else inactiveDateContainerColor)
    }

    @Composable
    override fun dateTextColor(active: Boolean): State<Color> {
        return rememberUpdatedState(if (active) activeDateTextColor else inactiveDateTextColor)
    }
}