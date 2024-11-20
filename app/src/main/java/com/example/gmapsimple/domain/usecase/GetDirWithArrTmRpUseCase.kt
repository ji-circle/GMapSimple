package com.example.gmapsimple.domain.usecase

import com.example.gmapsimple.domain.repository.DirectionsRepository

class GetDirWithArrTmRpUseCase
constructor(private val repository: DirectionsRepository) {
    suspend operator fun invoke(
        origin: String,
        destination: String,
        arrivalTime: Int,
        transitMode: String,
        transitRoutingPreference: String
    ) = repository.getDirectionsWithArrivalTmRp(
        origin,
        destination,
        arrivalTime,
        transitMode,
        transitRoutingPreference
    )
}