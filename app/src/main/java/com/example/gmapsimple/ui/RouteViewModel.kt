package com.example.gmapsimple.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gmapsimple.domain.model.DirectionsEntity
import com.example.gmapsimple.domain.usecase.GetDirectionsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RouteViewModel(private val getDirectionsUseCase: GetDirectionsUseCase) : ViewModel() {

    private val _routes = MutableStateFlow<List<DirectionsEntity>>(emptyList())
    val routes: StateFlow<List<DirectionsEntity>> = _routes

    private val _selectedRoute = MutableStateFlow<String?>(null)
    val selectedRoute: StateFlow<String?> = _selectedRoute

    fun fetchRoutes(origin: String, destination: String, apiKey: String) {
        viewModelScope.launch {
            try {
                val result = getDirectionsUseCase(origin, destination, apiKey)
                _routes.value = listOf(result)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun selectRoute(polyline: String) {
        _selectedRoute.value = polyline
    }

    fun clearSelection() {
        _selectedRoute.value = null
    }
}