package io.instabee.codetest.util

import kotlinx.datetime.Instant
import java.text.SimpleDateFormat
import java.util.Locale.UK

fun Instant.getHourMinute(): String = SimpleDateFormat("HH:mm", UK).format(toEpochMilliseconds())
fun Instant.getWeekdayDayMonthHourMinute(): String = SimpleDateFormat("EEE dd/MM HH:mm", UK).format(toEpochMilliseconds())
fun Instant.getWeekdayDayMonthHourMinuteTwoLines(): String =
    SimpleDateFormat("EEE dd/MM\nHH:mm", UK).format(toEpochMilliseconds())
