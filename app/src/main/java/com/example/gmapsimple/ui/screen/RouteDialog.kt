package com.example.gmapsimple.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
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
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.example.gmapsimple.ui.DirectionsViewModel

@Composable
fun RouteDialog(
    viewmodel: DirectionsViewModel,
    routes: List<String>,
    onIndexSelected: (Int) -> Unit,
    onDismiss: () -> Unit
) {
    var selectedRouteIndex by rememberSaveable { mutableStateOf<Int>(0) }
//    selectedRoute = viewmodel.selectedRouteIndex.value?: 0
    viewmodel.refreshIndex()

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("경로 선택하기") },
        text = {
            Column {
                RadioButtons(
                    routes,
                    selectedRouteIndex = selectedRouteIndex,
                    onChange = { selectedRouteIndex = it }
                )
                Text("왜 안떠")
            }
//
//            Column {
//                routes.forEachIndexed { index, route ->
//                    Row(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(8.dp)
//                            .clickable {
//                                selectedRouteIndex = index
//                            },
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//                        RadioButtons(
//                            routes,
//                            selectedRouteIndex)
//
//                        Text(text = "왜 안뜨지")
////                        RadioButton(
////                            selected = index == selectedRouteIndex,
////                            onClick = {
////                                selectedRouteIndex = index
////                                viewmodel.setSelectedRouteIndex(index)
////                            }
////                        )
////                        Text(
////                            text = route,
////                            modifier = Modifier.padding(start = 8.dp)
////                        )
//                    }
//                }
//            }
        },
        confirmButton = {
            Button(
                onClick = {
                    selectedRouteIndex.let { index ->
                        onIndexSelected(index)
//                        viewmodel.setSelectedRouteIndex(0)
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
                viewmodel.refreshIndex()
//                viewmodel.setSelectedRouteIndex(0)
            }
        }
    )
}

@Composable
fun RadioButtons(
    routes: List<String>,
    selectedRouteIndex: Int,
    onChange: (Int) -> Unit
) {
//    val selectedRoute = remember { mutableStateOf("")}
//    val isSelectedRoute: (String) -> Boolean = {selectedRoute.value == it}
//    val onChangeState: (String) -> Unit = {selectedRoute.value = it}
//    var selectedOption by rememberSaveable { mutableStateOf(routes[0]) }

//    val selectedIndex = remember { mutableStateOf(0)}
//    val isSelectedIndex: (Int) -> Boolean = {selectedIndex.value == it}
//    val onChangeStateIndex: (Int) -> Unit = {selectedIndex.value = it}

    Column(
        modifier = Modifier.padding(top = 10.dp)
    ) {
        routes.forEachIndexed{ index, route ->
            Row(
                verticalAlignment = Alignment.CenterVertically
            ){
                RadioButton(
                    selected = (index == selectedRouteIndex),
                    onClick = {onChange(index)}
                )
                Text(text = route)
            }

        }
//        routes.forEachIndexed { index, route ->
//            Column {
//                Row(
//                    modifier = Modifier
//                        .selectable(
////                            selected = isSelectedRoute(route),
////                            onClick = {onChangeState(route)},
//                            selected = isSelectedIndex(index),
//                            onClick = { onChangeStateIndex(index) },
//                            role = Role.RadioButton
//                        )
//                        .padding(bottom = 3.dp)
//                ) {
//                    RadioButton(
////                        selected = isSelectedRoute(route),
//                        selected = isSelectedIndex(index),
//                        onClick = null,
//                        modifier = Modifier.padding(end = 5.dp)
//                    )
//                    Text(text = route)
//
//                }
//            }
//        }
    }
}