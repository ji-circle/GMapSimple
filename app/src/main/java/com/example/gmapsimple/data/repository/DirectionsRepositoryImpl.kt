package com.example.gmapsimple.data.repository

import android.util.Log
import com.example.gmapsimple.data.remote.DirectionsApiService
import com.example.gmapsimple.domain.model.DirectionsEntity
import com.example.gmapsimple.domain.repository.DirectionsRepository

class DirectionsRepositoryImpl(
    private val apiService: DirectionsApiService
) : DirectionsRepository {

    override suspend fun getDirections(
        origin: String,
        destination: String,
        mode: String
    ): DirectionsEntity {
        val result = apiService.getDirections(origin, destination, mode).toEntity()
        Log.d("확인", "impl: $result")
        return result
    }

    override suspend fun getDirectionsWithDepartureTmRp(
        origin: String,
        destination: String,
        departureTime: Int,
        transitMode: String,
        transitRoutingPreference: String
    ): DirectionsEntity {
        val result = apiService.getDirectionsDep(
            origin,
            destination,
            departureTime,
            transitMode,
            transitRoutingPreference
        ).toEntity()
        Log.d("확인", "impl 8: $result")
        return result
    }

    override suspend fun getDirectionsWithTmRp(
        origin: String,
        destination: String,
        transitMode: String,
        transitRoutingPreference: String
    ): DirectionsEntity {
        val result =
            apiService.getDirectionsTmRp(origin, destination, transitMode, transitRoutingPreference)
                .toEntity()
        Log.d("확인", "impl 9: $result")
        return result
    }

    override suspend fun getDirectionsWithArrivalTmRp(
        origin: String,
        destination: String,
        arrivalTime: Int,
        transitMode: String,
        transitRoutingPreference: String
    ): DirectionsEntity {
        val result = apiService.getDirectionsArr(
            origin,
            destination,
            arrivalTime,
            transitMode,
            transitRoutingPreference
        ).toEntity()
        Log.d("확인", "impl 12: $result")
        return result
    }

}