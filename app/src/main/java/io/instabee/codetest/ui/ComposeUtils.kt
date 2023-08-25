package io.instabee.codetest.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun InstabeeListItem(modifier: Modifier = Modifier, block: @Composable () -> Unit) {
    Card(
        elevation = 4.dp,
        modifier = modifier
            .padding(horizontal = 8.dp)
            .padding(bottom = 6.dp, top = 2.dp)
    ) { block() }
}
