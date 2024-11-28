package com.example.onthisday.presentation

import com.example.onthisday.domain.HistoricEvent

sealed class EventsUiState {
    data class Display(val historicEvents: List<HistoricEventGroup>) : EventsUiState()
    data object Loading : EventsUiState()
    data class Error(val reason: String) : EventsUiState()
}

data class HistoricEventGroup(val date: String, val events: List<HistoricEvent>)