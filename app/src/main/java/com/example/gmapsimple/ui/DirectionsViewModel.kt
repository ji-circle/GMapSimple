package com.example.gmapsimple.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.gmapsimple.domain.model.DirectionsEntity
import com.example.gmapsimple.domain.model.DirectionsTransitDetailsEntity
import com.example.gmapsimple.domain.model.DirectionsTransitLineEntity
import com.example.gmapsimple.domain.model.DirectionsTransitStopEntity
import com.example.gmapsimple.domain.model.DirectionsTransitVehicleEntity
import com.example.gmapsimple.domain.model.LatLngEntity
import com.example.gmapsimple.domain.model.TimeZoneTextValueObjectEntity
import com.example.gmapsimple.domain.usecase.GetDirWithArrTmRpUseCase
import com.example.gmapsimple.domain.usecase.GetDirWithDepTmRpUseCase
import com.example.gmapsimple.domain.usecase.GetDirWithTmRpUseCase
import com.example.gmapsimple.domain.usecase.GetDirectionsUseCase
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions
import com.google.maps.android.PolyUtil
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalTime

class DirectionsViewModel(
//    private val repository: DirectionsRepository,
    private val getDirectionsUseCase: GetDirectionsUseCase,
    private val getDirWithDepTmRpUseCase: GetDirWithDepTmRpUseCase,
    private val getDirWithTmRpUseCase: GetDirWithTmRpUseCase,
    private val getDirWithArrTmRpUseCase: GetDirWithArrTmRpUseCase
) : ViewModel() {

    private val _directionsResult = MutableStateFlow<DirectionsEntity?>(null)
    val directionsResult: StateFlow<DirectionsEntity?> = _directionsResult

//    private val _directionsResult = MutableLiveData<DirectionsEntity?>()
//    val directionsResult: LiveData<DirectionsEntity?> get() = _directionsResult

    private val _selectedRouteIndex = MutableStateFlow<Int?>(0)
    val selectedRouteIndex: StateFlow<Int?> = _selectedRouteIndex


//    private val _selectedRouteIndex = MutableLiveData<Int>()
//    val selectedRouteIndex: LiveData<Int> get() = _selectedRouteIndex

    private val _polyLines = MutableLiveData<List<PolylineOptions>>()
    val polyLines: LiveData<List<PolylineOptions>> get() = _polyLines

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    //string 형태로 바꾸거나 문자로 검색한 출발지(사용자의 위치)
    private val _origin = MutableLiveData<String>()
    val origin: LiveData<String> get() = _origin

    //string 형태로 바꾸거나 문자로 검색한 도착지(목적지)
    private val _destination = MutableLiveData<String>()
    val destination: LiveData<String> get() = _destination

    //transit, driving, walking 등
    private val _mode = MutableLiveData<String>()
    val mode: LiveData<String> get() = _mode

    //더 선호하는 대중교통 수단
    private val _transitMode = MutableLiveData<String>()
    val transitMode: LiveData<String> get() = _transitMode

    //더 선호하는 방식 (less_walking 등)
    private val _routingPreference = MutableLiveData<String>()
    val routingPreference: LiveData<String> get() = _routingPreference

    //출/도착 시간 선택한 경우
    private val _selectedTime = MutableLiveData<LocalTime>()
    val selectedTime: LiveData<LocalTime> get() = _selectedTime

    //경로 선택하기 전 보여줄 간단한 소개들
    private val _routeSelectionText = MutableLiveData<List<String>>()
    val routeSelectionText: LiveData<List<String>> get() = _routeSelectionText

    //시간 선택 창 옆 버튼...
    private val _isDepArrNone = MutableLiveData<Int>(0)
    val isDepArrNone: LiveData<Int> get() = _isDepArrNone

//    private val _polyLine = MutableLiveData<List<PolylineOptions>>()
//    val polyLine: LiveData<List<PolylineOptions>> get() = _polyLine

    private val _polylines = MutableStateFlow<List<PolylineOptions>>(emptyList())
    val polylines: StateFlow<List<PolylineOptions>> = _polylines


    private val _latLngBounds = MutableLiveData<List<LatLngEntity>>()
    val latLngBounds: LiveData<List<LatLngEntity>> get() = _latLngBounds

    private val _userLocation = MutableLiveData<LatLng>()
    val userLocation: LiveData<LatLng> get() = _userLocation

    private val _destLocationLatLng = MutableLiveData<LatLng>()
    val destLocationLatLng: LiveData<LatLng> get() = _destLocationLatLng

    private val _directionExplanations = MutableLiveData<String>()
    val directionExplanations: LiveData<String> get() = _directionExplanations

    private val _shortExplanations = MutableLiveData<String>()
    val shortExplanations: LiveData<String> get() = _shortExplanations

    private val _startLocation = MutableLiveData<LatLng>()
    val startLocation: LiveData<LatLng> get() = _startLocation

    private val _country = MutableLiveData<String>()
    val country: LiveData<String> = _country


    fun setSelectedRouteIndex(indexNum: Int) {
        _selectedRouteIndex.value = indexNum ?: 0
        Log.d("123123", "${indexNum}")
    }
    fun fetchDirections(origin: String, destination: String, mode: String) {
        viewModelScope.launch {
            try {
                val result = getDirectionsUseCase(
                    origin,
                    destination,
                    mode
                )
                _directionsResult.value = result
                setRouteSelectionText()
                Log.d("확인 routeSelectionText", routeSelectionText.value.toString())
            } catch (e: Exception) {
                _error.postValue(e.message)
            }
        }
    }

    fun getRouteSelectionList(): List<String>{
        val selectionList = mutableListOf<String>()

        routeSelectionText.value?.forEach { it->
            selectionList.add(it.toString())
            Log.d("확인 routes viewmodel", it.toString())
        }
        return selectionList
    }
    fun afterSelecting() {
        viewModelScope.launch {
            updatePolyLineWithColors()
//            updateBounds()
            //setShortDirectionsResult()
            setDirectionsResult()
        }
    }

    fun setDirectionsResult() {
        if (_directionsResult.value != null) {
            formatDirectionsExplanations(_directionsResult.value!!)
        } else {
            _error.postValue("_direction null")
            Log.d("확인 setDirections", "null")
        }
    }

    private fun setRouteSelectionText() {
        if (_directionsResult.value != null) {
            Log.d("확인 setDirections", "${_directionsResult.value}")
            formatRouteSelectionText(_directionsResult.value!!)
        } else {
            _error.postValue("출발지와 목적지를 다시 확인해 주세요.")
            Log.d("확인 setDirections", "null")
            _routeSelectionText.postValue(emptyList())
            //emptyOrNull
        }
    }

    private fun formatRouteSelectionText(directions: DirectionsEntity) {
        val resultsList = mutableListOf<String>()
        refreshIndex()

        directions.routes.size
        var routeIndex = 1
        directions.routes.forEach { route ->
            val resultText = StringBuilder()
            val resultText1 = StringBuilder()

            resultText.append("🔵경로 ${routeIndex}\n")
            route.legs.forEach { leg ->
                resultText1.append("  예상 소요 시간 : ${leg.totalDuration.text}")
                if (mode.value == "transit") {
                    resultText.append("\n🕐${leg.totalArrivalTime.text}에 도착 예정입니다.\n")
                } else {
                    resultText.append("\n")
                }
                resultText1.append("\n")

                val resultText2 = StringBuilder()

                var num = 1
                leg.steps.forEach { step ->
                    resultText2.append("✦${num}:")
                    if (step.travelMode == "TRANSIT") {
                        if (step.transitDetails.line.shortName != "") {
                            resultText2.append(" [${step.transitDetails.line.shortName}]")
                        } else if (step.transitDetails.line.name != "") {
                            resultText2.append(" [${step.transitDetails.line.name}]")
                        } else {
                            //
                        }
                    }
                    Log.d("확인 travelMode", "${step.travelMode.toString()}")

                    resultText2.append(" ${step.htmlInstructions} (${step.stepDuration.text})\n")
                    num++
                }
                resultText1.append(resultText2)
            }
            resultText.append(resultText1)
            resultsList.add(resultText.toString())
            routeIndex++
        }
        Log.d("확인 리스트 인덱스", "${resultsList.size}")
        _routeSelectionText.value = resultsList
        Log.d("확인 setDirections", "stringbuilder ${resultsList}")
    }

    fun refreshIndex() {
        _selectedRouteIndex.value = 0
    }

    fun selectRoute(index: Int) {
        _selectedRouteIndex.value = index
        updatePolyLineWithColors()
    }

    private fun updatePolyLineWithColors() {
        try {
            val routes = _directionsResult.value?.routes
            val polylines = mutableListOf<PolylineOptions>()

            routes?.get(_selectedRouteIndex.value ?: 0)?.legs?.forEach { leg ->
                leg.steps.forEach { step ->
                    val decodedPoints = PolyUtil.decode(step.polyline.points ?: "")
                    val color = hexToColorInt(step.transitDetails?.line?.color ?: "#FF0000")

                    val coloredLine = PolylineOptions()
                        .addAll(decodedPoints)
                        .width(10f)
                        .color(color)

                    polylines.add(coloredLine)
                }
            }
            _polylines.value = polylines
            _polyLines.postValue(polylines)
        } catch (e: Exception) {
            _error.postValue(e.message)
        }
    }

    private fun hexToColorInt(hexColor: String): Int {
        return try {
            android.graphics.Color.parseColor("#${hexColor.removePrefix("#")}")
        } catch (e: IllegalArgumentException) {
            android.graphics.Color.GRAY
        }
    }

    private fun formatDirectionsExplanations(directions: DirectionsEntity) {
        val resultText = StringBuilder()
        val finalText = StringBuilder()
        Log.d("확인 index 상태", "${selectedRouteIndex.value}")

        directions.routes.get(_selectedRouteIndex.value!!).legs.forEach { leg ->
            resultText.append("🗺️목적지까지 ${leg.totalDistance.text},\n")
            resultText.append("앞으로 ${leg.totalDuration.text} 뒤")
            if (mode.value == "transit") {
                resultText.append("인\n🕐${leg.totalArrivalTime.text}에 도착 예정입니다.\n")
            } else {
                resultText.append(" 도착 예정입니다.\n")
            }
            resultText.append("\n")
            var num = 1
            val resultText1 = StringBuilder()
            leg.steps.forEach { step ->
                resultText1.append("🔷${num}\n")
                resultText1.append("*  상세설명:")

                if (step.travelMode == "TRANSIT") {
                    if (step.transitDetails.line.shortName != "") {
                        resultText1.append(" [${step.transitDetails.line.shortName}]")
                    } else if (step.transitDetails.line.name != "") {
                        resultText1.append(" [${step.transitDetails.line.name}]")
                    } else {
                        //
                    }
                }
                Log.d("확인 travelMode", "${step.travelMode.toString()}")

                resultText1.append(" ${step.htmlInstructions}\n")
                resultText1.append("*  소요시간: ${step.stepDuration.text}\n")
                resultText1.append("*  구간거리: ${step.distance.text}\n")

                if (step.transitDetails != DirectionsTransitDetailsEntity(
                        DirectionsTransitStopEntity(LatLngEntity(0.0, 0.0), ""),
                        TimeZoneTextValueObjectEntity("", "", 0.0),
                        DirectionsTransitStopEntity(LatLngEntity(0.0, 0.0), ""),
                        TimeZoneTextValueObjectEntity("", "", 0.0),
                        (""),
                        0,
                        DirectionsTransitLineEntity(
                            emptyList(),
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            DirectionsTransitVehicleEntity("", "", "", "")
                        ),
                        0,
                        ""
                    )
                ) {
                    resultText1.append("|    탑승 장소: ${step.transitDetails.departureStop.name}\n")
                    resultText1.append("|    하차 장소: ${step.transitDetails.arrivalStop.name}\n")
                    resultText1.append("|    ${step.transitDetails.numStops}")
                    resultText1.append(categorizeTransportation(step.transitDetails.line.vehicle.type))
                    resultText1.append("\n\n")
                } else {
                    resultText1.append("\n\n\n")
                }

                num++
            }
            resultText.append(resultText1)
        }
        _directionExplanations.value = resultText.toString()
    }

    // 교통 수단을 카테고라이즈하는 메서드
    private fun categorizeTransportation(transportationType: String): String {
        return when (transportationType) {
            "BUS" -> {
                "개 정류장 이동🚍\n"
            }

            "CABLE_CAR" -> {
                " 케이블 카 이용🚟\n"
            }

            "COMMUTER_TRAIN" -> {
                "개 역 이동🚞\n"
            }

            "FERRY" -> {
                " 페리 이용⛴️\n"
            }

            "FUNICULAR" -> {
                " 푸니큘러 이용🚋\n"
            }

            "GONDOLA_LIFT" -> {
                " 곤돌라 리프트 이용🚠\n"
            }

            "HEAVY_RAIL" -> {
                "개 역 이동🛤️\n"
            }

            "HIGH_SPEED_TRAIN" -> {
                "개 역 이동🚄\n"
            }

            "INTERCITY_BUS" -> {
                "개 정류장 이동🚌\n"
            }

            "LONG_DISTANCE_TRAIN" -> {
                "개 역 이동🚂\n"
            }

            "METRO_RAIL" -> {
                "개 역 이동🚇\n"
            }

            "MONORAIL" -> {
                "개 역 이동🚝\n"
            }

            "OTHER" -> {
                " 이동\n"
            }

            "RAIL" -> {
                "개 역 이동🚃\n"
            }

            "SHARE_TAXI" -> {
                " 공유 택시 이용🚖\n"
            }

            "SUBWAY" -> {
                "개 역 이동🚉\n"
            }

            "TRAM" -> {
                "개 역 트램으로 이동🚊\n"
            }

            "TROLLEYBUS" -> {
                "개 정류장 트롤리버스로 이동🚎\n"
            }

            else -> {
                " 이동\n"
            }
        }
    }
}

class DirectionsViewModelFactory(
    private val getDirectionsUseCase: GetDirectionsUseCase,
    private val getDirWithDepTmRpUseCase: GetDirWithDepTmRpUseCase,
    private val getDirWithTmRpUseCase: GetDirWithTmRpUseCase,
    private val getDirWithArrTmRpUseCase: GetDirWithArrTmRpUseCase
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DirectionsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DirectionsViewModel(
                getDirectionsUseCase,
                getDirWithDepTmRpUseCase,
                getDirWithTmRpUseCase,
                getDirWithArrTmRpUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

