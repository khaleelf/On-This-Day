package com.example.onthisday.presentation

import com.example.onthisday.domain.HistoricEvent

sealed class OnThisDayUiState {
    data class Display(val historicEvents: List<HistoricEventGroup>) : OnThisDayUiState()
    data object Loading : OnThisDayUiState()
    data class Error(val reason: String) : OnThisDayUiState()
}

data class HistoricEventGroup(val date: String, val events: List<HistoricEvent>)