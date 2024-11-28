package com.example.onthisday.presentation

import com.example.onthisday.domain.HistoricEvent

sealed class EventsUiState {
    data class Display(val historicEvents: List<HistoricEvent>) : EventsUiState()
    data object Loading : EventsUiState()
    data class Error(val reason: String) : EventsUiState()
}

fun HistoricEvent.id(): String {
    return "$title#$year".hashCode().toString(16)
}