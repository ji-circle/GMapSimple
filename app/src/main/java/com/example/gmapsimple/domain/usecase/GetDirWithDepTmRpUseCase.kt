package com.example.gmapsimple.domain.usecase

import com.example.gmapsimple.domain.repository.DirectionsRepository

class GetDirWithDepTmRpUseCase
constructor(private val repository: DirectionsRepository) {
    suspend operator fun invoke(
        origin: String,
        destination: String,
        departureTime: Int,
        transitMode: String,
        transitRoutingPreference: String
    ) = repository.getDirectionsWithDepartureTmRp(
        origin,
        destination,
        departureTime,
        transitMode,
        transitRoutingPreference
    )
}