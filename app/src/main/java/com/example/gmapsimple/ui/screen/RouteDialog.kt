package com.example.gmapsimple.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gmapsimple.ui.DirectionsViewModel

@Composable
fun RouteDialog(
    viewmodel: DirectionsViewModel,
    routes: List<String>,
    onRouteSelected: (Int) -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Select a Route") },
        text = {
            Column {
                routes.forEachIndexed { index, route ->
                    Text(
                        text = route,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onRouteSelected(index)
                                viewmodel.setSelectedRouteIndex(index)
                            }
                            .padding(8.dp)
                    )
                }
            }
        },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}