package io.instabee.codetest.ui.routelist

import io.instabee.codetest.api.model.Route
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.minus
import org.junit.Assert.*

import org.junit.Test
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.hours

class RouteListFragmentKtTest {

    @Test
    fun isCurrentlyCheckedIn() {
        val routeThatIMissingStart = Route.mock().copy(checkedInAt = null)
        assertFalse(isCurrentlyCheckedIn(routeThatIMissingStart))

        val routeThatIsCheckedOutInTheFuture = Route.mock().copy(checkedOutAt = Clock.System.now().minus(1.days))
        assertFalse(isCurrentlyCheckedIn(routeThatIsCheckedOutInTheFuture))

        val routeThatIsValid = Route.mock().copy(checkedInAt =  Clock.System.now().minus(1.hours))
        assertTrue(isCurrentlyCheckedIn(routeThatIsValid))
    }
}