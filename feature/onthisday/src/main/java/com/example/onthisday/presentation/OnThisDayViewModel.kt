package com.example.onthisday.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onthisday.domain.Date
import com.example.onthisday.domain.EventsRepository
import com.example.onthisday.domain.HistoricEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class OnThisDayViewModel @Inject constructor(
    private val eventsRepository: EventsRepository,
) : ViewModel() {

    init {
        viewModelScope.launch {
            getEvents().toString()
        }
    }

    suspend fun getEvents() {
        val localDate = LocalDate.now()
        val eventsResult = eventsRepository.getEventsResult(
            Date(
                day = localDate.dayOfMonth,
                month = localDate.monthValue
            )
        )
    }
}

sealed class EventsUiState {
    data object Loading : EventsUiState()
    data class Events(val historicEvents: List<HistoricEvent>) : EventsUiState()
    data class Error(val reason: String) : EventsUiState()
}
