package com.example.gmapsimple.ui

import com.example.gmapsimple.data.remote.DirectionsApiService
import com.example.gmapsimple.data.remote.RouteNetworkClient
import com.example.gmapsimple.data.remote.RouteNetworkClient.directionsApiService
import com.example.gmapsimple.data.repository.DirectionsRepositoryImpl
import com.example.gmapsimple.domain.repository.DirectionsRepository
import com.example.gmapsimple.domain.usecase.GetDirWithArrTmRpUseCase
import com.example.gmapsimple.domain.usecase.GetDirWithDepTmRpUseCase
import com.example.gmapsimple.domain.usecase.GetDirWithTmRpUseCase
import com.example.gmapsimple.domain.usecase.GetDirectionsUseCase
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServiceLocator {

    private val directionsApiService = RouteNetworkClient.directionsApiService


    val directionsRepository: DirectionsRepository by lazy {
        DirectionsRepositoryImpl(directionsApiService)
    }

    val getDirectionsUseCase: GetDirectionsUseCase by lazy {
        GetDirectionsUseCase(directionsRepository)
    }

    val getDirWithTmRpUseCase: GetDirWithTmRpUseCase by lazy {
        GetDirWithTmRpUseCase(directionsRepository)
    }

    val directionsContainer: DirectionsContainer by lazy {
        DirectionsContainer(
            getDirectionsUseCase,
            getDirWithDepTmRpUseCase,
            getDirWithTmRpUseCase,
            getDirWithArrTmRpUseCase
        )
    }

    val getDirWithDepTmRpUseCase: GetDirWithDepTmRpUseCase by lazy {
        GetDirWithDepTmRpUseCase(directionsRepository)
    }


    val getDirWithArrTmRpUseCase: GetDirWithArrTmRpUseCase by lazy {
        GetDirWithArrTmRpUseCase(directionsRepository)
    }
}

class DirectionsContainer(
    private val getDirectionsUseCase: GetDirectionsUseCase,
    private val getDirWithDepTmRpUseCase: GetDirWithDepTmRpUseCase,
    private val getDirWithTmRpUseCase: GetDirWithTmRpUseCase,
    private val getDirWithArrTmRpUseCase: GetDirWithArrTmRpUseCase
) {
    val directionsViewModelFactory = DirectionsViewModelFactory(
        getDirectionsUseCase,
        getDirWithDepTmRpUseCase,
        getDirWithTmRpUseCase,
        getDirWithArrTmRpUseCase
    )
}