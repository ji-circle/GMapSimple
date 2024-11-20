package com.example.gmapsimple

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gmapsimple.ui.DirectionsViewModel
import com.example.gmapsimple.ui.DirectionsViewModelFactory
import com.example.gmapsimple.ui.RouteViewModel
import com.example.gmapsimple.ui.ServiceLocator
import com.example.gmapsimple.ui.screen.GoogleMapScreen
import com.example.gmapsimple.ui.screen.InputScreen
import com.example.gmapsimple.ui.screen.RouteDialog
import com.example.gmapsimple.ui.screen.RouteScreen
import com.example.gmapsimple.ui.theme.GMapSimpleTheme

class MainActivity : ComponentActivity() {

    private lateinit var viewModel: DirectionsViewModel
//    private val serviceLocator = (application as GMapSimpleApplication).appContainer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val serviceLocator = (application as GMapSimpleApplication).appContainer

        viewModel = serviceLocator.directionsContainer.directionsViewModelFactory.create(DirectionsViewModel::class.java)

        setContent {
            var showDialog by remember { mutableStateOf(false) }
            var showMap by remember { mutableStateOf(false) }
            var origin by remember { mutableStateOf("") }
            var destination by remember { mutableStateOf("") }

            if (!showMap) {
                InputScreen (
                    origin = origin,
                    destination = destination,
                    onOriginChange = { origin = it },
                    onDestinationChange = { destination = it },
                    onSearchClicked = {
                        viewModel.fetchDirections(
                            origin,
                            destination,
                            "transit"
                        )
                        showDialog = true
                    }
                )
            } else {
                GoogleMapScreen(viewModel = viewModel)
            }

            if (showDialog) {
                val routes = viewModel.routeSelectionText.value?: emptyList()
                Log.d("확인 routes", routes.toString())
                RouteDialog(
                    viewModel,
                    routes = routes,
                    onRouteSelected = {
                        viewModel.selectRoute(it)
                        showMap = true
                        showDialog = false
                    },
                    onDismiss = { showDialog = false }
                )
                showMap = true
            }
        }
    }
}
