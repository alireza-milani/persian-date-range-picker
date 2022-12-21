package com.alirezamilani.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.alirezamilani.app.ui.theme.PersianDateRangePickerTheme
import com.alirezaMilani.persianDateRangePicker.DateRangePicker

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PersianDateRangePickerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DateRangePicker(
                        yearRange = IntRange(1400, 1401),
                        onCloseClick = {  },
                        onConfirmClick = { _, _ ->}
                    )
                }
            }
        }
    }
}