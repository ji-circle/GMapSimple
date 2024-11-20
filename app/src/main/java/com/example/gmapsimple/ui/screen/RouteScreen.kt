package com.example.gmapsimple.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RouteScreen(
    onSearch: (String, String) -> Unit
) {
    var origin by remember { mutableStateOf("") }
    var destination by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        BasicTextField(
            value = origin,
            onValueChange = { origin = it },
            modifier = Modifier.fillMaxWidth(),
            decorationBox = { Text("Enter Origin") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        BasicTextField(
            value = destination,
            onValueChange = { destination = it },
            modifier = Modifier.fillMaxWidth(),
            decorationBox = { Text("Enter Destination") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { onSearch(origin, destination) }) {
            Text("Search Route")
        }
    }
}