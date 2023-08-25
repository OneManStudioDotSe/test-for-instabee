package io.instabee.codetest.util

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun Instant.isToday(): Boolean = toLocalDateTime(TimeZone.currentSystemDefault()).date == Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date

fun Instant.coerceUntilNow(): Instant = Clock.System.now().let { now -> if (this < now) this else now }
