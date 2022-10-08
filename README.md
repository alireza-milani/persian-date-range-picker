# Persian Date RangePicker

Date pickers let users select range of dates, written in **Jetpack compose**
<br>
Inspired by https://material.io/components/date-pickers
<br>
<br>
Date pickers let users select range of dates. They should be suitable for the context in which they appear.
- Dialogs on mobile

## Mobile date range picker
Mobile date range pickers allow selection of a range of dates. They cover the entire screen.
<br>
Common use cases include:
- Booking a flight
- Reserving a hotel

![date-picker_1](https://user-images.githubusercontent.com/13084877/194699781-b9dc67e8-5dea-4b77-b688-174f42e628f0.png)

- Title
- Selected date range
- Month and year label
- Current date
- Start date
- Selected range
- End date

## Download
```kotlin
repositories {
  maven("https://jitpack.io")
}
```
```kotlin
implementation("com.github.alireza-milani:persian-date-range-picker:0.0.1")
```

## Usage
### For a date picker with no customization
```kotlin
DateRangePicker(
  modifier: Modifier = Modifier,
  onCloseClick = { 
    // Do something when close button is selected
  },
  onConfirmClick = { startDate, endDate ->
    // Do something with selected startDate and endDate
  }
)
```
## License

    Copyright 2022 Alireza Milani

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

