package com.example.gmapsimple.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
    var selectedRoute by rememberSaveable { mutableStateOf<Int>(0) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("경로 선택하기") },
        text = {
            Column {
                routes.forEachIndexed { index, route ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .clickable {
                                selectedRoute = index
                            },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = index == selectedRoute,
                            onClick = {
                                selectedRoute = index
                            }
                        )
                        Text(
                            text = route,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    selectedRoute.let { route ->
                        onRouteSelected(route)
                    }
                    onDismiss()
                }
            ) {
                Text("선택완료")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("취소")
//                viewmodel.setSelectedRouteIndex(0)
            }
        }
    )
}