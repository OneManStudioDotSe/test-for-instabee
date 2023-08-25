package io.instabee.codetest.repository

import android.content.SharedPreferences
import io.instabee.codetest.api.MainApi
import io.instabee.codetest.api.model.Route
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onEach

class RouteRepo(
    private val api: MainApi,
    private val pref: SharedPreferences,
) {
    val stateRoutes: StateFlow<List<Route>> = MutableStateFlow(emptyList())

    fun getRoutesApi(): Flow<List<Route>> = api.getRoutes().onEach {
        (stateRoutes as MutableStateFlow).value = it.sortedBy { route ->
            route.scheduledStartDateTime
        }
    }
}
