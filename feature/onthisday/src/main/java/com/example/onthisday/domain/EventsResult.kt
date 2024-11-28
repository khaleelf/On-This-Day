package com.example.onthisday.domain

sealed class EventsResult {
    data class Success(val historicEvents: List<HistoricEvent>) : EventsResult()
    data class Error(val reason: String) : EventsResult()
}