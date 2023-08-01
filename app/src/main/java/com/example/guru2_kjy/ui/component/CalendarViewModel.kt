package com.example.guru2_kjy.ui.component

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.guru2_kjy.data.model.Travel

import com.example.guru2_kjy.data.repository.TravelRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.Instant
import org.threeten.bp.temporal.ChronoUnit
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(private val travelRepository: TravelRepository) :
    ViewModel() {

    val ioContext = Dispatchers.Default + SupervisorJob()
    val nextScreen = MutableStateFlow(0L)

    fun inputTravel(areaName: String, imageKey: String, startDate: Long, endDate: Long) {
        viewModelScope.launch(ioContext) {
            val item = Travel.TravelItem(
                areaName = areaName,
                imageKey = imageKey,
                startDate = getTimeStampToDateTime(startDate) ?: "",
                endDate = getTimeStampToDateTime(endDate) ?: "",
                calculateTravelDays = calculateTravelDays(startDate, endDate)
            )
            nextScreen.value = travelRepository.insertTravel(item)
        }
    }

    private fun calculateTravelDays(startDate: Long, endDate: Long): Int {
        val startInstant = Instant.ofEpochMilli(startDate)
        val endInstant = Instant.ofEpochMilli(endDate)
        return ChronoUnit.DAYS.between(startInstant, endInstant).toInt() + 1
    }

    private fun getTimeStampToDateTime(millis: Long): String? {
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        return formatter.format(Date(millis))
    }
}