package com.example.guru2_kjy.ui.schedule.check

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.guru2_kjy.data.model.Travel

import com.example.guru2_kjy.data.repository.TravelRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.flow.singleOrNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.Instant
import org.threeten.bp.temporal.ChronoUnit
import java.text.SimpleDateFormat
import java.util.ArrayList
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class ScheduleCheckViewModel @Inject constructor(private val travelRepository: TravelRepository) :
    ViewModel() {

    val ioContext = Dispatchers.Default + SupervisorJob()
    val nextScreen = MutableStateFlow(0L)

    val travel = MutableStateFlow(Travel.TravelItem())
    private val todoList = MutableStateFlow(emptyList<Travel.TravelToDoList>())


    val list:StateFlow<List<Travel.TravelToDoList>> = travel.combine(todoList) { item, list ->
        var arrayList = arrayListOf<Travel.TravelToDoList>()
        for(i in 0 .. (item.calculateTravelDays -1)) {
            var isFind = false
            run {
                list.forEach { bList ->
                    if (bList.day == i) {
//                        isFind = true
                        arrayList.add(bList)
//                        return@run
                    }
                }
            }
            if(!isFind) {
                arrayList.add(Travel.TravelToDoList(day = i))
            }
        }
        arrayList
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = emptyList()
    )

    fun getTravel(nid: Long) {
        viewModelScope.launch(ioContext) {
            travelRepository.getTravel(nid).collectLatest {
                travel.emit(it?:return@collectLatest)
            }
        }
    }

    fun getToDoList(travelNid: Long) {
        if(0 < travelNid) {
            viewModelScope.launch(ioContext) {
                travelRepository.getToDoList(travelNid).collectLatest {
                    todoList.emit(it)
                }
            }
        }
    }

}