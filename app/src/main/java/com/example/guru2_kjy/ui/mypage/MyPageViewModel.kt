package com.example.guru2_kjy.ui.mypage

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
class MyPageViewModel @Inject constructor(private val travelRepository: TravelRepository) :
    ViewModel() {

    val ioContext = Dispatchers.Default + SupervisorJob()


    fun deleteAll() {
        viewModelScope.launch(ioContext) {
            travelRepository.deleteAll()
        }
    }
}