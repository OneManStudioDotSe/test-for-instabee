package io.instabee.codetest.api.model

import io.instabee.codetest.util.coerceUntilNow
import io.instabee.codetest.util.getWeekdayDayMonthHourMinuteTwoLines
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.Instant
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.serialization.SerialName
import java.util.UUID
import kotlin.random.Random

/**
 * @param scheduledStartDateTime Timestamp of when the route is/was scheduled to start.
 * @param checkedInAt Timestamp of when any courier last checked into this route. It's a past datetime and can be null.
 * @param checkedOutAt Timestamp of when any courier last checked out of this route. It's a past datetime and can be null.
 */
data class Route(
    val id: String,
    val name: String,
    @SerialName("scheduled_start_date_time") val scheduledStartDateTime: Instant,
    val stops: List<Stop> = emptyList(),
    @SerialName("checked_in_at") val checkedInAt: Instant? = null,
    @SerialName("checked_out_at") val checkedOutAt: Instant? = null,
) {
    val scheduledStartWeekdayDayMonthHourMinute: String? by lazy { scheduledStartDateTime.getWeekdayDayMonthHourMinuteTwoLines() }

    companion object {
        private val mockedStopsList = (1..100).map { Stop.mock() }.distinctBy { it.id }

        fun mock() = Route(
            id = UUID.randomUUID().toString(),
            name = (1..50).map { "STO EM $it" }.random(),
            scheduledStartDateTime = Clock.System.now().plus((-20 * 3600..20 * 3600).random(), DateTimeUnit.SECOND),
            stops = mockedStopsList.shuffled().take(Random.nextInt(5, 25)).let { stops ->
                val visitedNr = Random.nextInt(0, stops.size - 4)
                stops.mapIndexed { i, stop -> if (i < visitedNr) stop.copy(visited = true) else stop }
            },
            checkedInAt = null,
            checkedOutAt = null,
        ).let { route ->
            if (Random.nextFloat() > 0.7) route
            else route.copy(checkedInAt = route.scheduledStartDateTime.let { scheduledStartTime ->
                if (scheduledStartTime.minus(1, DateTimeUnit.HOUR) < Clock.System.now()) scheduledStartTime else null
            }?.plus((-3600..3600).random(), DateTimeUnit.SECOND)?.coerceUntilNow())
        }.let { route ->
            if (route.checkedInAt == null || Random.nextFloat() > 0.5) route
            else route.copy(checkedOutAt = route.checkedInAt.plus((-3600..5 * 3600).random(), DateTimeUnit.SECOND).coerceUntilNow())
        }
    }
}
