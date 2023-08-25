package io.instabee.codetest.ui.route

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Input
import androidx.compose.material.icons.filled.KeyboardReturn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import io.instabee.codetest.api.model.Stop
import io.instabee.codetest.ui.GrayIsh
import io.instabee.codetest.ui.GreyGray
import io.instabee.codetest.ui.InstabeeListItem
import io.instabee.codetest.ui.PinkIsh
import io.instabee.codetest.util.getHourMinute
import kotlinx.datetime.Clock
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class RouteDetailsFragment : Fragment() {
    private val args: RouteDetailsFragmentArgs by navArgs()
    private val vm: RouteDetailsViewModel by viewModel { parametersOf(args.routeId) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        ComposeView(requireContext()).apply {
            setContent {
                val uiState by vm.stateUi.collectAsState()
                RouteDetailsScreen(uiState)
            }
        }
}

@Composable
fun RouteDetailsScreen(uiState: RouteDetailsUiState) {
    MaterialTheme {
        CollapsingToolbarScaffold(
            state = rememberCollapsingToolbarScaffoldState(),
            toolbar = {
                if (uiState is RouteDetailsUiState.Ui) {
                    Row(
                        Modifier
                            .background(PinkIsh)
                            .padding(16.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = uiState.route.name,
                            style = MaterialTheme.typography.h5,
                            modifier = Modifier
                                .wrapContentWidth(align = Alignment.Start)
                                .weight(1f, true),
                        )
                        Text(
                            text = uiState.route.scheduledStartWeekdayDayMonthHourMinute ?: "",
                            style = MaterialTheme.typography.subtitle1,
                            modifier = Modifier.wrapContentWidth(align = Alignment.End),
                            textAlign = TextAlign.End,
                        )
                    }
                }
            },
            modifier = Modifier,
            scrollStrategy = ScrollStrategy.EnterAlways,
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                when (uiState) {
                    is RouteDetailsUiState.Empty -> Text("No stops available. Try reloading.")
                    is RouteDetailsUiState.Ui -> LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(uiState.route.stops, key = { it.id }) {
                            StopListItem(stop = it)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StopListItem(stop: Stop) {
    InstabeeListItem {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            val itemColor = if (stop.etaDateTime < Clock.System.now()) GrayIsh else GreyGray
            Column(
                Modifier
                    .wrapContentWidth(align = Alignment.Start)
                    .weight(weight = 1f, fill = true)
            ) {
                Text(text = stop.name, color = itemColor)
                Spacer(modifier = Modifier.size(4.dp))
                Text(
                    text = stop.etaDateTime.getHourMinute(),
                    color = itemColor,
                )
            }
            Row(modifier = Modifier.wrapContentWidth(align = Alignment.End)) {
                Icon(Icons.Default.Input, contentDescription = null, tint = itemColor)
                Spacer(modifier = Modifier.size(2.dp))
                Text(text = stop.dropOffIds.size.toString(), color = itemColor)
                Spacer(modifier = Modifier.size(4.dp))
                Icon(Icons.Default.KeyboardReturn, contentDescription = null, tint = itemColor)
                Spacer(modifier = Modifier.size(2.dp))
                Text(text = stop.pickupIds.size.toString(), color = itemColor)
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun RouteDetailsPreview() {
    RouteDetailsScreen(RouteDetailsUiState.Ui.mock())
}

@Composable
@Preview(showBackground = true)
fun RouteDetailsEmptyPreview() {
    RouteDetailsScreen(RouteDetailsUiState.Empty)
}
