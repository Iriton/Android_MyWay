package com.example.guru2_kjy.ui.travel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.guru2_kjy.data.model.Travel
import com.example.guru2_kjy.data.repository.TravelRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val travelRepository: TravelRepository) :
    ViewModel() {

    val ioContext = Dispatchers.Default + SupervisorJob()

    val travelList: StateFlow<List<Travel.TravelItem>> =
        travelRepository.getTravels()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(1000L),
                initialValue = emptyList()
            )
}