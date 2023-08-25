package io.instabee.codetest.ui.routelist

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.instabee.codetest.api.model.Route
import io.instabee.codetest.repository.RouteRepo
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class RouteListViewModel(
    private val routeRepo: RouteRepo,
    private val pref: SharedPreferences,
) : ViewModel() {
    val stateUi = routeRepo.stateRoutes
        .map {
            if (it.isEmpty()) RouteListUiState.Empty else RouteListUiState.Ui(it.sortedBy { route ->
                route.scheduledStartDateTime
            })
        }
        .stateIn(viewModelScope, SharingStarted.Eagerly, RouteListUiState.Empty)

    fun refreshRoutes() {
        viewModelScope.launch {
            routeRepo.getRoutesApi().collect()
        }
    }
}

sealed class RouteListUiState {
    object Empty : RouteListUiState()
    data class Ui(val routeList: List<Route>) : RouteListUiState() {
        companion object {
            fun mock() = Ui((0..10).map { Route.mock() }.distinctBy { it.id })
        }
    }
}
