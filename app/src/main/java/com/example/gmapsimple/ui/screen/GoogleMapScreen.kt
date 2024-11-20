package com.example.gmapsimple.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.Observer
import com.example.gmapsimple.ui.DirectionsViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapFragment
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.PolyUtil
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.Route

@Composable
fun GoogleMapScreen(viewModel: DirectionsViewModel, modifier: Modifier = Modifier) {
    viewModel.afterSelecting()
    val initLatLng = LatLng(37.5664056, 126.9778222)

    var showDialog by rememberSaveable { mutableStateOf(false) }
    var selectedRoute by rememberSaveable { mutableStateOf<Route?>(null) }

    val routes = viewModel.directionExplanations.value.toString()

    val polylines = viewModel.polylines.collectAsState(initial = emptyList()).value

    runBlocking {
//        launch {
//            viewModel.afterSelecting()
//        }
        launch {

            //TODO 예외처리

            MapFragment().apply {
                getMapAsync { p0 ->
                    polylines.forEach { polylineOptions ->
                        p0.addPolyline(polylineOptions)

                    }
                    p0.moveCamera(CameraUpdateFactory.newLatLngZoom(initLatLng, 10.0f))
                }
            }
        }
    }
//    MapFragment().apply {
//        getMapAsync(object: OnMapReadyCallback {
//            override fun onMapReady(p0: GoogleMap) {
//                polylines.forEach { polylineOptions ->
//                    p0.addPolyline(polylineOptions)
//
//                }
//                p0.moveCamera(CameraUpdateFactory.newLatLngZoom(initLatLng,10.0f))
//            }
//        })
//    }

}