package io.instabee.codetest.ui.routelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Checkbox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import io.instabee.codetest.api.model.Route
import io.instabee.codetest.ui.GrayIsh
import io.instabee.codetest.ui.GreyGray
import io.instabee.codetest.ui.InstabeeListItem
import io.instabee.codetest.util.getHourMinute
import io.instabee.codetest.util.getWeekdayDayMonthHourMinute
import io.instabee.codetest.util.isToday
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.format.DateTimeFormatter

class RouteListFragment : Fragment() {
    private val vm: RouteListViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        ComposeView(requireContext()).apply {
            setContent {
                val uiState by vm.stateUi.collectAsState()
                RouteListScreen(uiState) { route -> onRouteClicked(route) }
            }
        }

    private fun onRouteClicked(route: Route) {
        findNavController().navigate(
            RouteListFragmentDirections.actionRouteListFragmentToRouteDetailsFragment(
                routeId = route.id
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm.refreshRoutes()
    }
}

@Composable
fun RouteListScreen(uiState: RouteListUiState, onRouteClick: (Route) -> Unit) {
    MaterialTheme {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            when (uiState) {
                is RouteListUiState.Empty -> Text("No routes available. Try reloading.")
                is RouteListUiState.Ui -> LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(uiState.routeList, key = { it.id }) {
                        RouteListItem(route = it, onRouteClick = onRouteClick)
                    }
                }
            }
        }
    }
}

fun isCurrentlyCheckedIn(route: Route) : Boolean {
    return if(route.checkedOutAt != null) {
        false
    } else {
        val rightNow = Clock.System.now()
        route.checkedInAt != null && (route.checkedInAt < rightNow)
    }
}

@Composable
fun RouteListItem(route: Route, onRouteClick: (Route) -> Unit) {
    InstabeeListItem(modifier = Modifier.clickable { onRouteClick(route) }) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            val textColor = if (route.scheduledStartDateTime < Clock.System.now()) GrayIsh else GreyGray
            Text(text = route.name, color = textColor)
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                text = route.scheduledStartDateTime.run { if (isToday()) getHourMinute() else getWeekdayDayMonthHourMinute() },
                color = textColor
            )
            Checkbox(checked = (route.checkedInAt != null), onCheckedChange = { isChecked ->

            })
        }
    }
}

@Composable
@Preview(showBackground = true)
fun RouteListPreview() {
    RouteListScreen(RouteListUiState.Ui.mock()) {}
}

@Composable
@Preview(showBackground = true)
fun RouteListEmptyPreview() {
    RouteListScreen(RouteListUiState.Empty) {}
}
