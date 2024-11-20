package com.example.gmapsimple.domain.usecase

import com.example.gmapsimple.domain.repository.DirectionsRepository

class GetDirectionsUseCase
constructor(private val repository: DirectionsRepository) {
    suspend operator fun invoke(
        origin: String,
        destination: String,
        mode: String
    ) =
        repository.getDirections(origin, destination, mode)
}