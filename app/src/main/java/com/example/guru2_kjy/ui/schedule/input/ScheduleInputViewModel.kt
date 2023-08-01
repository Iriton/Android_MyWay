package com.example.guru2_kjy.ui.schedule.input

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.guru2_kjy.data.model.Travel

import com.example.guru2_kjy.data.repository.TravelRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch
import org.threeten.bp.Instant
import org.threeten.bp.temporal.ChronoUnit
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class ScheduleInputViewModel @Inject constructor(private val travelRepository: TravelRepository) :
    ViewModel() {

    val ioContext = Dispatchers.Default + SupervisorJob()
    val nextScreen = MutableStateFlow(0L)

    val todoNid = MutableStateFlow(0L)

    fun setToDoList(travelNid: Long, day: Int, place: String, time: String, transport: String, money: String, memo: String) {
        viewModelScope.launch(ioContext) {
            todoNid.value = travelRepository.insertToDoList(Travel.TravelToDoList(
                travelNid = travelNid,
                day = day,
                place = place,
                todoTime = time,
                money = money,
                transport = transport,
                memo = memo
            ))
        }
    }

}