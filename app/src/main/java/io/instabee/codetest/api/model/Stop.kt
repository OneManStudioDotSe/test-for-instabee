package io.instabee.codetest.api.model

import androidx.annotation.Keep
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.Instant
import kotlinx.datetime.plus
import kotlinx.serialization.SerialName
import java.util.UUID
import kotlin.random.Random

@Keep
data class Stop(
    val id: String,
    @SerialName("eta_date_time") val etaDateTime: Instant,
    val name: String,
    val street: String,
    val zip: String,
    val city: String,
    val country: String,
    @SerialName("drop_off_ids") val dropOffIds: List<String> = emptyList(),
    @SerialName("pickup_ids") val pickupIds: List<String> = emptyList(),
    val visited: Boolean,
) {
    fun getFormattedAddress(): String = "$street, $zip $city"

    companion object {
        fun mock() = Stop(
            id = UUID.randomUUID().toString(),
            etaDateTime = Clock.System.now().plus((-3600..3600).random(), DateTimeUnit.SECOND),
            name = (1..50).map { "IN$it Generic Stop Name" }.random(),
            street = (1..50).map { "Drottninggatan $it" }.random(),
            zip = "111 36",
            city = "Stockholm",
            country = "Sweden",
            dropOffIds = (0..100).map { "$it" }.shuffled().take(Random.nextInt(5, 25)),
            pickupIds = (0..100).map { "$it" }.shuffled().take(Random.nextInt(5, 25)),
            visited = false
        )
    }
}
