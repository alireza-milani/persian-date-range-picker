package com.alirezaMilani.persianDateRangePicker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.ContentAlpha
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import com.alirezaMilani.persianDateRangePicker.persianCalendar.PersianCalendar

/**
 * A header layout to show in top of [DateRangePicker]
 *
 * @author Alireza Milani
 * @since 1.0.0
 */
@Composable
fun HeaderDate(
    colors: DateRangePickerColors,
    state: DateRangePickerState,
    saveLabel: String,
    title: String,
    onCloseClick: () -> Unit,
    onConfirmClick: (start: PersianCalendar, end: PersianCalendar) -> Unit
) {
    CompositionLocalProvider(LocalContentColor provides colors.headerContentColor) {
        Column(
            modifier = Modifier
                .background(colors.headerContainerColor)
                .fillMaxWidth()
                .defaultMinSize(minHeight = DateRangePickerTokens.HeaderHeight)
                .padding(
                    start = DateRangePickerTokens.HeaderHorizontalPadding,
                    top = DateRangePickerTokens.HeaderTopPadding,
                    end = DateRangePickerTokens.HeaderHorizontalPadding,
                    bottom = DateRangePickerTokens.HeaderBottomPadding
                )
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = onCloseClick) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close calendar"
                    )
                }

                TextButton(
                    onClick = {
                        onConfirmClick(
                            PersianCalendar(state.selectedStartDate!!),
                            PersianCalendar(state.selectedEndDate!!)
                        )
                    },
                    enabled = state.isSelectionComplete(),
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = LocalContentColor.current,
                        disabledContentColor = LocalContentColor.current.copy(alpha = ContentAlpha.disabled)
                    )
                ) {
                    Text(
                        text = saveLabel,
                        modifier = Modifier.padding(horizontal = DateRangePickerTokens.SaveTextButtonPadding)
                    )
                }
            }

            Text(
                modifier = Modifier.padding(start = DateRangePickerTokens.HeaderTextPadding),
                text = title,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                style = MaterialTheme.typography.labelMedium
            )

            Text(
                modifier = Modifier
                    .paddingFromBaseline(DateRangePickerTokens.HeaderSelectionTextPaddingFromBaseline)
                    .padding(start = DateRangePickerTokens.HeaderTextPadding)
                    .fillMaxWidth(),
                text = state.updateHeader(),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontSize = DateRangePickerTokens.HeaderSelectionTextSize
                )
            )
        }
    }
}