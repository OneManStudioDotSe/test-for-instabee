package io.instabee.codetest.ui.route

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.instabee.codetest.api.model.Route
import io.instabee.codetest.repository.RouteRepo
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class RouteDetailsViewModel(
    private val routeId: String,
    private val routeRepo: RouteRepo,
) : ViewModel() {
    val stateUi = routeRepo.stateRoutes.map { it.find { route -> route.id == routeId } }
        .map { if (it == null) RouteDetailsUiState.Empty else RouteDetailsUiState.Ui(it) }
        .stateIn(viewModelScope, SharingStarted.Eagerly, RouteDetailsUiState.Empty)

    fun refreshRoute() {
        TODO("Not yet implemented")
    }
}

sealed class RouteDetailsUiState {
    object Empty : RouteDetailsUiState()
    data class Ui(val route: Route) : RouteDetailsUiState() {
        companion object {
            fun mock() = Ui(Route.mock())
        }
    }
}
